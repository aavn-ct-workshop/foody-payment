package com.aa.workshop.kafka.payment.entity;

import lombok.Data;

@Data
public class OrderItem {
    String id;
    String name;
    long price;
    int quantity;
}