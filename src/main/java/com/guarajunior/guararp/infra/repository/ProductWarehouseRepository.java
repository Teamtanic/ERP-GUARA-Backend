package com.guarajunior.guararp.infra.repository;

import com.guarajunior.guararp.infra.model.ProductWarehouse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface ProductWarehouseRepository extends JpaRepository<ProductWarehouse, UUID> {
	ProductWarehouse findByProduct(String product);
	
	@Query("SELECT pw FROM ProductWarehouse pw WHERE pw.active = true")
	Page<ProductWarehouse> findAll(Pageable pageable);
}
