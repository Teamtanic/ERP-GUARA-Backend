package com.guarajunior.guararp.api.dto.productwarehouse.response;

import com.guarajunior.guararp.infra.model.SupplierProduct;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class ProductWarehouseResponse {
	private UUID id;
	private String product;
	private Integer quantity;
	private List<SupplierProduct> supplierProducts;
}
