package com.guarajunior.guararp.model.dto.productwarehouse;

import jakarta.persistence.Column;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class ProductWarehouseDTO {
	private String product;
	private Integer quantity;
	private UUID companyRelationship;
	@Column(precision = 10, scale = 2)
	private BigDecimal supplierPrice;
}
