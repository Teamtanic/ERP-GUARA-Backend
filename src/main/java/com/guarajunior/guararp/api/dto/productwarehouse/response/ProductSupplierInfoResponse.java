package com.guarajunior.guararp.api.dto.productwarehouse.response;

import com.guarajunior.guararp.infra.model.SupplierProduct;
import jakarta.persistence.Column;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class ProductSupplierInfoResponse {
    private String product;
    @Column(precision = 10, scale = 2)
    private BigDecimal supplierPrice;
    private UUID companyRelationshipId;
    private UUID productWarehouseId;
}