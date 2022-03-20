package com.aa.workshop.kafka.payment.entity;

import lombok.Data;

@Data
public class FoodyWallet {
    String uuid;
    String userId;
    long timestamp;
    long balance;
}
