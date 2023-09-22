package com.guarajunior.rp.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.guarajunior.rp.model.BankAccount;

public interface BankAccountRepository extends JpaRepository<BankAccount, UUID> {
	@Query("SELECT ba FROM BankAccount ba WHERE ba.active = true")
	Page<BankAccount> findAll(Pageable pageable);
}
