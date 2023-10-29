package com.guarajunior.guararp.infra.repository;

import com.guarajunior.guararp.infra.model.SupplierProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierProductRepository extends JpaRepository<SupplierProduct, SupplierProduct.SupplierProductKey> {

}
