package com.guarajunior.rp.model.dto.bankaccount;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class BankAccountResponseDTO {
	private String name;
	private BigDecimal balance;
	private String location;
}
