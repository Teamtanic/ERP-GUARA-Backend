package com.guarajunior.guararp.repository;

import com.guarajunior.guararp.model.SupplierProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierProductRepository extends JpaRepository<SupplierProduct, SupplierProduct.SupplierProductKey> {

}
