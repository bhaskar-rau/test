package com.vizen.entity;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentData<T> {
	private List<T> payments;
}