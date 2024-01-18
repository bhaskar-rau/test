	package com.vizen.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Setter;
import lombok.Getter;

@Entity
@Table(name = "payment")
@Setter
@Getter
public class Payment {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "number")
	private String number;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "bill_value")
	private int billValue;
	
	@Column(name = "card_number")
	private String cardNumber;
	
	@Column(name = "card_holder")
	private String cardHolder;
	
	@Column(name = "date_value")
	private String dateValue;
	
	@Column(name = "cvc")
	private String cvc;
	
}