package com.guarajunior.guararp.api.dto.productwarehouse.response;

import com.guarajunior.guararp.infra.model.SupplierProduct;
import jakarta.persistence.Column;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductSupplierInfoResponse {
    private String product;
    @Column(precision = 10, scale = 2)
    private BigDecimal supplierPrice;
    private SupplierProduct.SupplierProductKey id;

	public ProductSupplierInfoResponse() {
    }
    
    public ProductSupplierInfoResponse(String product, BigDecimal supplierPrice) {
        this.product = product;
        this.supplierPrice = supplierPrice;
    }
}