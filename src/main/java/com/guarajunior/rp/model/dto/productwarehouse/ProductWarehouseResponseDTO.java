package com.guarajunior.rp.model.dto.productwarehouse;

import java.util.List;

import lombok.Data;

@Data
public class ProductWarehouseResponseDTO {
	private String product;
	private Integer quantity;
	private List<ProductSupplierInfoDTO> supplierProducts;
}
