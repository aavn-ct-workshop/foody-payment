package com.aa.workshop.kafka.payment.entity;

import org.apache.kafka.common.serialization.Serdes.WrapperSerde;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

public class FoodyWalletSerdes extends WrapperSerde<FoodyWallet> {

    public FoodyWalletSerdes() {
        super(new JsonSerializer<>(), new JsonDeserializer<>(FoodyWallet.class));
    }
}