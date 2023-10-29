package com.guarajunior.guararp.model.dto.transaction;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.guarajunior.guararp.enums.TransactionType;
import com.guarajunior.guararp.model.dto.bankaccount.BankAccountResponseDTO;
import com.guarajunior.guararp.model.dto.productwarehouse.ProductTransactionDTO;
import com.guarajunior.guararp.model.dto.project.ProjectResponseDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class TransactionResponseDTO {
	private String description;
	private BigDecimal amount;
	private TransactionType type;
	private String paymentMethod;
	private Integer installments;
	private Integer qtyInstallments;
	@JsonFormat(pattern = "dd/MM/yyyy") 
	@JsonSerialize(using = LocalDateSerializer.class) 
	private LocalDate dtCashflow;
	private ProjectResponseDTO project;
	private BankAccountResponseDTO bankAccount;
	private List<ProductTransactionDTO> products;
}
