package com.vizen.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vizen.entity.Payment;
import com.vizen.entity.PaymentData;
import com.vizen.repository.PaymentRepository;

@Service
public class PaymentServiceImpl implements PaymentService {

	private PaymentRepository paymentRepository;
	
	@Autowired
	public PaymentServiceImpl(PaymentRepository thePaymentRepository) {
		paymentRepository = thePaymentRepository;
	}

	@Override
	public PaymentData<Payment> findAll() {
		List<Payment> payment = paymentRepository.findAll();
		PaymentData<Payment> response = new PaymentData<>();
		response.setPayments(payment);
		return response;
	}

	@Override
	public Payment findById(Integer theId) {
		Optional<Payment> result = paymentRepository.findById(theId);
		
		Payment thePayment = null;
        if(result.isPresent()) {
        	thePayment = result.get();
        } else {
            // WE COULDN'T FIND THE PAYMENT WITH THAT ID
            throw new RuntimeException("Did not find the Payment - " + theId);
        }

        return thePayment;
	}

	@Override
	public Payment save(Payment thePayment) {
		return paymentRepository.save(thePayment);
	}

	@Override
	public void deleteById(int theId) {
		paymentRepository.deleteById(theId);
	}
	
}