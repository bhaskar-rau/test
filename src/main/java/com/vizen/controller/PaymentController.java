package com.vizen.controller;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.vizen.entity.Payment;
import com.vizen.entity.PaymentData;
import com.vizen.service.PaymentService;

import io.swagger.annotations.Api;

@Api(tags = "Payment Mgmt")
@RestController
@Validated
@RequestMapping("/api")
public class PaymentController {

	private PaymentService paymentService;
	
	
	public PaymentController(PaymentService thePaymentService) {
		paymentService = thePaymentService;
	}
	
	@GetMapping("/payments")
	public PaymentData<Payment> findAll() {
		return paymentService.findAll();
	}
	@GetMapping("/payments/details/{paymentId}")
	public ResponseEntity<Payment> findbyId(@PathVariable Integer paymentId) {
	    if (paymentId == null) {
	        // Handle the case where the ID is null, for example, return a 400 Bad Request
	        return ResponseEntity.badRequest().build();
	    }
	    Payment payment = paymentService.findById(paymentId);

	    // Handle the result and return an appropriate response
	    if (payment != null) {
	        return ResponseEntity.ok(payment);
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}
//	@GetMapping("/payments/details/{paymentId}")
//	public Payment findbyId(@PathVariable  Integer paymentId) {
//		Payment payment = paymentService.findById(paymentId);
//		return payment;
//	}	
	
	@PostMapping("/payments/create")
    public Payment addPayment(@RequestBody Payment thePayment) {
        // JUST IN CASE AN ID IS PASSED, SETTING IT TO 0
        thePayment.setId(0);
        Payment dbPayment = paymentService.save(thePayment);
        return dbPayment;
    }

    @PutMapping("/payments/update")
    public Payment updatePayment(@RequestBody Payment thePayment) {
    	Payment dbPayment = paymentService.save(thePayment);
        return dbPayment;
    }

    @DeleteMapping("/payments/delete/{paymentId}")
    public String deletePayment(@PathVariable int paymentId) {
    	Payment thePayment = paymentService.findById(paymentId);

        if(thePayment == null) {
            throw new RuntimeException("Payment Not Found - " + paymentId);
        }

        paymentService.deleteById(paymentId);

        return "Successfully deleted Payment";
    }
}
