package com.guarajunior.guararp.api.dto.productwarehouse.response;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class ProductWarehouseResponse {
	private UUID id;
	private String product;
	private Integer quantity;
	private List<ProductSupplierInfoResponse> supplierProducts;
}
