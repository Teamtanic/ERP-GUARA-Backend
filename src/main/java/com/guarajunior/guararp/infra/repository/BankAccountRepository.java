package com.guarajunior.guararp.infra.repository;

import com.guarajunior.guararp.infra.model.BankAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface BankAccountRepository extends JpaRepository<BankAccount, UUID> {
	@Query("SELECT ba FROM BankAccount ba WHERE ba.active = true")
	Page<BankAccount> findAll(Pageable pageable);

	@Query("SELECT ba FROM BankAccount ba WHERE LOWER(ba.name) LIKE CONCAT('%', :searchTerm, '%') AND ba.active = true")
    Page<BankAccount> findByName(@Param("searchTerm") String searchTerm, Pageable pageable);
}
