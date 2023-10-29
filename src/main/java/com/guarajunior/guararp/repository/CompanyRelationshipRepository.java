package com.guarajunior.guararp.repository;


import com.guarajunior.guararp.enums.BusinessRelationshipType;
import com.guarajunior.guararp.model.Company;
import com.guarajunior.guararp.model.CompanyRelationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface CompanyRelationshipRepository extends JpaRepository<CompanyRelationship, UUID> {
	@Query("SELECT cr FROM CompanyRelationship cr WHERE cr.company = :company AND cr.businessRelationship = :businessRelationshipType")
	Optional<CompanyRelationship> findByCompanyAndIdBusinessRelationship(@Param("company") Company idCompany, @Param("businessRelationshipType") BusinessRelationshipType businessRelationshipType);
}
