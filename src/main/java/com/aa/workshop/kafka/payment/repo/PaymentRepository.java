package com.aa.workshop.kafka.payment.repo;

import java.util.List;

import com.aa.workshop.kafka.payment.entity.Payment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    
    List<Payment> findByUserId(String userId);
}
