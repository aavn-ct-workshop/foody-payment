package com.aa.workshop.kafka.payment.control;

import java.sql.Timestamp;
import java.time.Instant;

import com.aa.workshop.kafka.payment.entity.BalanceResponse;
import com.aa.workshop.kafka.payment.entity.FoodyOrder;
import com.aa.workshop.kafka.payment.entity.FoodyWallet;
import com.aa.workshop.kafka.payment.entity.Payment;
import com.aa.workshop.kafka.payment.entity.PaymentData;
import com.aa.workshop.kafka.payment.repo.PaymentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class PaymentManagement {
    @Autowired private KafkaTemplate<String, FoodyOrder> ordersTemplate;
    @Autowired private KafkaTemplate<String, FoodyWallet> paymentsTemplate;
    @Autowired private PaymentRepository paymentRepo;

    @KafkaListener(topics = "orders", containerFactory = "foodyOrderKafkaListenerContainerFactory")
    public void listenOrders(FoodyOrder record) {
        if(!"VALIDATING".equals(record.getStatus())) {
            return;
        }
        log.info("FoodyOrder: {}", record);
        handlePaymentFoodyOrder(record);
    }

    private void handlePaymentFoodyOrder(FoodyOrder value) {
        FoodyOrder handledOrder = value;
        Long balance = paymentRepo.findByUserId(value.getHostId()).stream().mapToLong(Payment::getAmount).sum();
        Timestamp ts = Timestamp.from(Instant.now());
        
        if(balance >= value.getPrice()) {
            updateBalance(new PaymentData(value.getHostId(), value.getPrice() * -1, "Paid for order id " + value.getId()));
            log.info("Produce message back to orders topic - {}", handledOrder);
            paymentsTemplate.send("payments", new FoodyWallet(value.getId(), value.getHostId(), value.getCreateDate(), ts.getTime(), true));
        } else {
            log.info("Produce message back to orders topic - {}", handledOrder);
            paymentsTemplate.send("payments", new FoodyWallet(value.getId(), value.getHostId(), value.getCreateDate(), ts.getTime(), false));
        }
    }

    public void updateBalance(PaymentData data) {
        Payment payment = new Payment();
        payment.setUserId(data.getUserId());
        payment.setAmount(data.getAmount());
        payment.setDetail(data.getDetail());
        paymentRepo.save(payment);
    }

    public BalanceResponse getBalance(String userId) {
        return new BalanceResponse(userId, paymentRepo.findByUserId(userId).stream().mapToLong((value) -> value.getAmount()).sum());
    }

    public void pushOrder(FoodyOrder data) {
        ordersTemplate.send("orders", data); 
    }
    
}
