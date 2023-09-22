package com.guarajunior.rp.repository;


import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.guarajunior.rp.enums.BusinessRelationshipType;
import com.guarajunior.rp.model.Company;
import com.guarajunior.rp.model.CompanyRelationship;

public interface CompanyRelationshipRepository extends JpaRepository<CompanyRelationship, UUID> {
	@Query("SELECT cr FROM CompanyRelationship cr WHERE cr.company = :company AND cr.businessRelationship = :businessRelationshipType")
	Optional<CompanyRelationship> findByCompanyAndIdBusinessRelationship(@Param("company") Company idCompany, @Param("businessRelationshipType") BusinessRelationshipType businessRelationshipType);
}
