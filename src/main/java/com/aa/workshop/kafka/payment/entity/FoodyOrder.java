package com.aa.workshop.kafka.payment.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodyOrder {
    String uuid;
    String userId;
    long timestamp;
    long price;
    boolean isPaid;
}
