package com.guarajunior.rp.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.guarajunior.rp.enums.TransactionType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

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
