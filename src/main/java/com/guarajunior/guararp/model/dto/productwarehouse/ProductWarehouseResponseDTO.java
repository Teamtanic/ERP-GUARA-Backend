package com.guarajunior.guararp.model.dto.productwarehouse;

import lombok.Data;

import java.util.List;

@Data
public class ProductWarehouseResponseDTO {
	private String product;
	private Integer quantity;
	private List<ProductSupplierInfoDTO> supplierProducts;
}
