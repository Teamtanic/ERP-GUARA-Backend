package com.guarajunior.guararp.infra.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
@Table(name = "supplier_product")
public class SupplierProduct {
	@EmbeddedId
	private SupplierProductKey id;
	
	@ManyToOne
	@MapsId("companyRelationshipId")
	@JoinColumn(name = "id_company_relationship")
	private CompanyRelationship companyRelationship;

	@ManyToOne
	@MapsId("productWarehouseId")
	@JoinColumn(name = "id_product_warehouse")
	private ProductWarehouse product;
	
	@Column(name = "price", precision = 10, scale = 2)
	private BigDecimal price;
	
    @Data
    @Embeddable
    public static class SupplierProductKey implements Serializable {
		private static final long serialVersionUID = 1L;

		@Column(name = "id_company_relationship")
        private UUID companyRelationshipId;

        @Column(name = "id_product_warehouse")
        private UUID productWarehouseId;
    }
}
