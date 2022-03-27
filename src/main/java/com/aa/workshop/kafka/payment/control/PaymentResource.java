package com.aa.workshop.kafka.payment.control;

import com.aa.workshop.kafka.payment.entity.BalanceResponse;
import com.aa.workshop.kafka.payment.entity.FoodyOrder;
import com.aa.workshop.kafka.payment.entity.PaymentData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentResource {

  @Autowired
  PaymentManagement management;

  @PostMapping("/orders")
  void updateBalance(@RequestBody FoodyOrder data) {
    management.pushOrder(data);
  }

  @PostMapping("/wallets")
  void updateBalance(@RequestBody PaymentData data) {
    management.updateBalance(data);
  }

  @GetMapping("/wallets")
  BalanceResponse getBalance(@RequestParam String userId) {
    return management.getBalance(userId);
  }

}
