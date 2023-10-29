package com.guarajunior.guararp.infra.repository;

import com.guarajunior.guararp.infra.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID>{
	Page<Transaction> findAll(Pageable pageable);
}
