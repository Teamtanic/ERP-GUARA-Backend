package com.guarajunior.rp.model.dto.productwarehouse;

import java.math.BigDecimal;

import com.guarajunior.rp.model.SupplierProduct.SupplierProductKey;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class ProductSupplierInfoDTO {
    private String product;
    @Column(precision = 10, scale = 2)
    private BigDecimal supplierPrice;
    private SupplierProductKey id;

	public ProductSupplierInfoDTO() {
    }
    
    public ProductSupplierInfoDTO(String product, BigDecimal supplierPrice) {
        this.product = product;
        this.supplierPrice = supplierPrice;
    }
}