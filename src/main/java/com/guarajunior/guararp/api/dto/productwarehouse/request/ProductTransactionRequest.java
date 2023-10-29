package com.guarajunior.guararp.api.dto.productwarehouse.request;

import lombok.Data;

import java.util.UUID;

@Data
public class ProductTransactionRequest {
	private UUID productId;
	private Integer quantity;
}
