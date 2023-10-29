package com.guarajunior.guararp.model.dto.bankaccount;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BankAccountDTO {
	private String name;
	private BigDecimal balance;
	private String location;
}
