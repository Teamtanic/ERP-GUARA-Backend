package com.guarajunior.guararp.repository;

import com.guarajunior.guararp.model.BankAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface BankAccountRepository extends JpaRepository<BankAccount, UUID> {
	@Query("SELECT ba FROM BankAccount ba WHERE ba.active = true")
	Page<BankAccount> findAll(Pageable pageable);
}
