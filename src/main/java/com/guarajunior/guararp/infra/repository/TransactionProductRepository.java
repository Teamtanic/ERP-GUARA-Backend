package com.guarajunior.guararp.infra.repository;

import com.guarajunior.guararp.infra.model.TransactionProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionProductRepository extends JpaRepository<TransactionProduct, TransactionProduct.TransactionProductKey> {

}
