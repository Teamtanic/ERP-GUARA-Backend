package com.guarajunior.guararp.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Entity
public class BankAccount {
	@Id
	@GeneratedValue(generator = "UUID")
	private UUID id;
	private String name;
	@Column(precision = 10, scale = 2)
	private BigDecimal balance;
	private String location;
	private Boolean active;
	private UUID createdBy;
	@Column(name = "created_at")
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date createdAt;
	private UUID updatedBy;
	@Column(name = "updated_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;
	@OneToMany(mappedBy = "bankAccount")
	private List<Transaction> transactions;
}