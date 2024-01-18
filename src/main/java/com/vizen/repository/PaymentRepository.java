package com.vizen.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vizen.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

}