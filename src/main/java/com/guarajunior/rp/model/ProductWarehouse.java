package com.guarajunior.rp.model;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
public class ProductWarehouse {
	@Id
	@GeneratedValue(generator = "UUID")
	private UUID id;

	private String product;
	private Integer quantity;
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
	
	@OneToMany(mappedBy = "product")
    private List<SupplierProduct> supplierProducts;
	
	@ManyToMany(mappedBy = "productWarehouse")
	private List<Transaction> transactions;
}
