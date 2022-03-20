package com.aa.workshop.kafka.payment.control;

import com.aa.workshop.kafka.payment.entity.FoodyOrder;
import com.aa.workshop.kafka.payment.entity.FoodyWallet;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class PaymentManagement {
    @Autowired
    private KafkaTemplate<String, FoodyOrder> kafkaTemplate;
    private StreamsBuilderFactoryBean factoryBean;

    @KafkaListener(topics = "orders", containerFactory = "foodyOrderKafkaListenerContainerFactory")
    public void listenOrders(FoodyOrder record) {
        if(record.isPaid()) {
            return;
        }
        log.info("FoodyOrder: {}", record);
        handlePaymentFoodyOrder(record);
    }

    private void handlePaymentFoodyOrder(FoodyOrder value) {
        FoodyOrder handledOrder = value;
        handledOrder.setPaid(true);
        log.info("Produce message back to orders topic - {}", handledOrder);
        kafkaTemplate.send("orders", handledOrder);
    }

    public void getBalance(String userId) {
        KafkaStreams kafkaStreams = factoryBean.getKafkaStreams();
        ReadOnlyKeyValueStore<String, FoodyWallet> counts = kafkaStreams
            .store(StoreQueryParameters.fromNameAndType("userId", QueryableStoreTypes.keyValueStore()));

        log.info("myKStream: {}", kafkaStreams);
    }

    
}
