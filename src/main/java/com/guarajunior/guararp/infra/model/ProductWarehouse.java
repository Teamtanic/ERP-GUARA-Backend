package com.guarajunior.guararp.infra.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Entity
public class ProductWarehouse {
	@Id
	@GeneratedValue(generator = "UUID")
	private UUID id;

	private String product;
	private Integer quantity;
	private Boolean active = true;
	
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
	
	@OneToMany(mappedBy = "productWarehouse")
	private List<TransactionProduct> transactions;
}
