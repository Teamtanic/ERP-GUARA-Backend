package com.guarajunior.rp.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.guarajunior.rp.enums.OfferingType;
import com.guarajunior.rp.model.Offering;

public interface OfferingRepository extends JpaRepository<Offering, UUID> {
	@Query("SELECT DISTINCT o"
		+ " FROM Offering o"
		+ " WHERE o.type = :offeringType"
		+ " AND o.active = true")
	Page<Offering> findByOfferingType(@Param("offeringType") OfferingType offeringType, Pageable pageable);
	
	@Query("SELECT o FROM Offering o WHERE o.active = true")
	Page<Offering> findAll(Pageable pageable);
}
