package com.guarajunior.rp.model.dto.productwarehouse;

import java.util.UUID;

import lombok.Data;

@Data
public class ProductTransactionDTO {
	private UUID productId;
	private Integer quantity;
}
