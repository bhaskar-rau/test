package com.vizen.service;

import com.vizen.entity.Payment;
import com.vizen.entity.PaymentData;

public interface PaymentService {

	PaymentData<Payment> findAll();

	Payment findById(Integer theId);

	Payment save(Payment thePayment);

    void deleteById(int theId);
	
}