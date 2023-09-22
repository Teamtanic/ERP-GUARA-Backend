package com.guarajunior.rp.model.dto.productwarehouse;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class ProductWarehouseDTO {
	private String product;
	private Integer quantity;
	private UUID companyRelationship;
	@Column(precision = 10, scale = 2)
	private BigDecimal supplierPrice;
}
