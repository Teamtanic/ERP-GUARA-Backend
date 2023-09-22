package com.guarajunior.rp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guarajunior.rp.model.SupplierProduct;

public interface SupplierProductRepository extends JpaRepository<SupplierProduct, SupplierProduct.SupplierProductKey> {

}
