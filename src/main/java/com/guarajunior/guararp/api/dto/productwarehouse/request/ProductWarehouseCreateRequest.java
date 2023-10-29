package com.guarajunior.guararp.api.dto.productwarehouse.request;

import jakarta.persistence.Column;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class ProductWarehouseCreateRequest {
	private String product;
	private Integer quantity;
	private UUID companyRelationship;
	@Column(precision = 10, scale = 2)
	private BigDecimal supplierPrice;
}
