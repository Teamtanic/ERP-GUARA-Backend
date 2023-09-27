package com.guarajunior.rp.model;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "transaction_has_product")
public class TransactionProduct {
	@EmbeddedId
	private TransactionProductKey id;
	
	@ManyToOne
	@MapsId("transactionId")
    @JoinColumn(name = "id_transaction")
    private Transaction transaction;

    @ManyToOne
    @MapsId("productWarehouseId")
    @JoinColumn(name = "id_product_warehouse")
    private ProductWarehouse productWarehouse;

    private Integer quantity;

    @Data
    @Embeddable
    public static class TransactionProductKey implements Serializable {
    	private static final long serialVersionUID = 1L;

		@Column(name = "id_transaction")
		private UUID idTransaction;
		
		@Column(name = "id_product_warehouse")
		private UUID productWarehouseId;
    }
}
