package com.guarajunior.rp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guarajunior.rp.model.TransactionProduct;

public interface TransactionProductRepository extends JpaRepository<TransactionProduct, TransactionProduct.TransactionProductKey> {

}
