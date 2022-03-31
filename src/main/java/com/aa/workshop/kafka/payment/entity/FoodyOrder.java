package com.aa.workshop.kafka.payment.entity;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodyOrder {
    String id;
    String hostId;
    String hostName;
    String driveId;
    String deliveryAddress;
    String status;
    long createDate;
    List<OrderItem> orderItems;

    public long getPrice() {
        return orderItems.stream().map(OrderItem::getPrice).collect(Collectors.summingLong(Long::longValue));
    }
}
