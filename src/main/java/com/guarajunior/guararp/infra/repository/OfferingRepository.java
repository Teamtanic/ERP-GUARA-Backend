package com.guarajunior.guararp.infra.repository;

import com.guarajunior.guararp.infra.enums.OfferingType;
import com.guarajunior.guararp.infra.model.Offering;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface OfferingRepository extends JpaRepository<Offering, UUID> {
	@Query("SELECT DISTINCT o"
		+ " FROM Offering o"
		+ " WHERE o.type = :offeringType"
		+ " AND o.active = true")
	Page<Offering> findByOfferingType(@Param("offeringType") OfferingType offeringType, Pageable pageable);
	
	@Query("SELECT o FROM Offering o WHERE o.active = true")
	Page<Offering> findAll(Pageable pageable);
	
	@Query("SELECT o FROM Offering o WHERE LOWER(o.description) LIKE LOWER(concat('%', :description, '%')) AND o.active = true")
	Page<Offering> findByDescriptionContaining(String description, Pageable pageable);
}
