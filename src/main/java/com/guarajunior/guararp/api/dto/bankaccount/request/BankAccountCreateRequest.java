package com.guarajunior.guararp.api.dto.bankaccount.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BankAccountCreateRequest {
	private String name;
	private BigDecimal balance;
	private String location;
}
