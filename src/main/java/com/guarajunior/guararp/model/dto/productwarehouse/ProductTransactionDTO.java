package com.guarajunior.guararp.model.dto.productwarehouse;

import lombok.Data;

import java.util.UUID;

@Data
public class ProductTransactionDTO {
	private UUID productId;
	private Integer quantity;
}
