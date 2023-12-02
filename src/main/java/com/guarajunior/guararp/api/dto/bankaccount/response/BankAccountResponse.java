package com.guarajunior.guararp.api.dto.bankaccount.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class BankAccountResponse {
	private UUID id;
	private String name;
	private BigDecimal balance;
	private String location;
}
