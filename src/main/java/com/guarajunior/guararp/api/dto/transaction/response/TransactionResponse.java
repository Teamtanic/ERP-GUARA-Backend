package com.guarajunior.guararp.api.dto.transaction.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.guarajunior.guararp.infra.enums.TransactionType;
import com.guarajunior.guararp.api.dto.bankaccount.response.BankAccountResponse;
import com.guarajunior.guararp.api.dto.productwarehouse.request.ProductTransactionRequest;
import com.guarajunior.guararp.api.dto.project.response.ProjectResponse;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class TransactionResponse {
	private String description;
	private BigDecimal amount;
	private TransactionType type;
	private String paymentMethod;
	private Integer installments;
	private Integer qtyInstallments;
	@JsonFormat(pattern = "dd/MM/yyyy") 
	@JsonSerialize(using = LocalDateSerializer.class) 
	private LocalDate dtCashflow;
	private ProjectResponse project;
	private BankAccountResponse bankAccount;
	private List<ProductTransactionRequest> products;
}
