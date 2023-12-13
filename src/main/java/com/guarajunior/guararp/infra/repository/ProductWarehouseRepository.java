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

	@Query("SELECT DISTINCT p FROM ProductWarehouse p JOIN p.supplierProducts sp JOIN sp.companyRelationship cr WHERE cr.company.id = :idCompany")
	Page<ProductWarehouse> findAllByCompanyId(UUID idCompany, Pageable pageable);
	
	@Query("SELECT DISTINCT p FROM ProductWarehouse p WHERE LOWER(p.product) LIKE LOWER(concat('%', :product, '%')) AND p.active = true")
	Page<ProductWarehouse> findByProductContaining(String product, Pageable pageable);
}
