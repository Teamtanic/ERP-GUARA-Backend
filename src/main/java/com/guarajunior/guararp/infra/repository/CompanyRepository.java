package com.guarajunior.guararp.infra.repository;

import com.guarajunior.guararp.infra.enums.BusinessRelationshipType;
import com.guarajunior.guararp.infra.model.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, UUID> {
	@Query("SELECT DISTINCT c "
			+ "FROM Company c JOIN c.companyRelationships cr "
			+ "WHERE cr.businessRelationship = :businessRelationshipType "
			+ "AND cr.active = true")
	Page<Company> findByBusinessRelationshipType(@Param("businessRelationshipType") BusinessRelationshipType businessRelationshipType, Pageable pageable);

	Page<Company> findAll(Pageable pageable);
	
	@Query("SELECT DISTINCT c FROM Company c " +
	           "WHERE EXISTS (" +
	           "    SELECT 1 FROM c.companyRelationships cr " +
	           "    WHERE cr.active = true" +
	           ")")
	Page<Company> findAllWithActiveRelationships(Pageable pageable);
	
	@Query("SELECT DISTINCT c FROM Company c " +
		       "WHERE LOWER(c.name) LIKE LOWER(concat('%', :name, '%')) ")
    Page<Company> findByNameContaining(@Param("name") String name, Pageable pageable);

    @Query("SELECT c FROM Company c JOIN c.companyRelationships cr " +
	           "WHERE LOWER(c.name) LIKE LOWER(concat('%', :name, '%')) " +
	           "AND cr.businessRelationship = :businessRelationshipType " +
	           "AND cr.active = true")
    Page<Company> findByNameContainingAndBusinessRelationshipType(
	            @Param("name") String name,
	            @Param("businessRelationshipType") BusinessRelationshipType businessRelationshipType,
	            Pageable pageable);
}
