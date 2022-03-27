package com.aa.workshop.kafka.payment.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentData {
    String userId;
    long amount;
    String detail;
}
