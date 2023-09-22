package com.guarajunior.rp.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.guarajunior.rp.model.ProductWarehouse;

public interface ProductWarehouseRepository extends JpaRepository<ProductWarehouse, UUID> {
	ProductWarehouse findByProduct(String product);
	
	@Query("SELECT pw FROM ProductWarehouse pw WHERE pw.active = true")
	Page<ProductWarehouse> findAll(Pageable pageable);
}
