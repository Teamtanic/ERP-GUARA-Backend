package com.guarajunior.guararp.repository;

import com.guarajunior.guararp.model.TransactionProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionProductRepository extends JpaRepository<TransactionProduct, TransactionProduct.TransactionProductKey> {

}
