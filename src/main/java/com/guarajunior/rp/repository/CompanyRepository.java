package com.guarajunior.rp.repository;

import java.util.UUID;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.guarajunior.rp.enums.BusinessRelationshipType;
import com.guarajunior.rp.model.Company;

public interface CompanyRepository extends JpaRepository<Company, UUID> {
	@Query("SELECT DISTINCT c "
			+ "FROM Company c JOIN c.companyRelationships cr "
			+ "WHERE cr.businessRelationship = :businessRelationshipType "
			+ "AND cr.active = true")
	Page<Company> findByBusinessRelationshipType(@Param("businessRelationshipType") BusinessRelationshipType businessRelationshipType, Pageable pageable);

	Page<Company> findAll(Pageable pageable);
}
