package com.guarajunior.guararp.api.dto.bankaccount.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BankAccountResponse {
	private String name;
	private BigDecimal balance;
	private String location;
}
