package com.guarajunior.guararp.api.dto.productwarehouse.response;

import lombok.Data;

import java.util.List;

@Data
public class ProductWarehouseResponse {
	private String product;
	private Integer quantity;
	private List<ProductSupplierInfoResponse> supplierProducts;
}
