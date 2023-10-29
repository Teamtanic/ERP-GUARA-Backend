package com.guarajunior.guararp.model.dto.productwarehouse;

import com.guarajunior.guararp.model.SupplierProduct.SupplierProductKey;
import jakarta.persistence.Column;
import lombok.Data;

import java.math.BigDecimal;

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