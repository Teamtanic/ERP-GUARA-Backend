package com.guarajunior.guararp.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.guarajunior.guararp.enums.TransactionType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Transaction {
	@Id
	@GeneratedValue(generator = "UUID")
	private UUID id;
	private String description;
	@Column(precision = 10, scale = 2)
	private BigDecimal amount;
	@Enumerated(EnumType.STRING)
    private TransactionType type;
	private String paymentMethod;
	private Integer installments;
	private Integer qtyInstallments;
	@JsonFormat(pattern = "dd/MM/yyyy") 
	@JsonSerialize(using = LocalDateSerializer.class) 
	@Column(name = "dt_cashflow")
	@Temporal(TemporalType.DATE) 
	private LocalDate dtCashflow;
	private UUID createdBy;
	
	@Column(name = "created_at")
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date createdAt;
	
	@OneToMany(mappedBy = "transaction")
	private List<TransactionProduct> products;
	
	@ManyToOne
	@JoinColumn(name = "id_bank_account", nullable = false, referencedColumnName = "id")
	private BankAccount bankAccount;
	
	@ManyToOne
	@JoinColumn(name = "id_project", nullable = true, referencedColumnName = "id")
	private Project project;
	

}
