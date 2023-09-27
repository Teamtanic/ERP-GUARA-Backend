
package com.guarajunior.rp.service;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RequestBody;

import com.guarajunior.rp.exception.EntityNotFoundException;
import com.guarajunior.rp.mapper.ProductWarehouseMapper;
import com.guarajunior.rp.model.CompanyRelationship;
import com.guarajunior.rp.model.ProductWarehouse;
import com.guarajunior.rp.model.SupplierProduct;
import com.guarajunior.rp.model.dto.productwarehouse.ProductWarehouseDTO;
import com.guarajunior.rp.model.dto.productwarehouse.ProductWarehouseResponseDTO;
import com.guarajunior.rp.repository.CompanyRelationshipRepository;
import com.guarajunior.rp.repository.ProductWarehouseRepository;
import com.guarajunior.rp.repository.SupplierProductRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;


@Service
public class WarehouseService {
	@Autowired
	private ProductWarehouseRepository productWarehouseRepository;
	@Autowired
	private CompanyRelationshipRepository companyRelationshipRepository;
	@Autowired
	private SupplierProductRepository supplierProductRepository;
	@Autowired
	private ProductWarehouseMapper productWarehouseMapper;

	public Page<ProductWarehouseResponseDTO> getAllItems(Integer page, Integer size){
		Pageable pageable = PageRequest.of(page, size);
    	Page<ProductWarehouse> productPage = productWarehouseRepository.findAll(pageable);
		return productWarehouseMapper.pageToResponsePageDTO(productPage);
	}
	
	@Transactional
	public ProductWarehouseResponseDTO createProduct(@Valid @RequestBody ProductWarehouseDTO product) {
		CompanyRelationship relation = companyRelationshipRepository.findById(product.getCompanyRelationship())
	            .orElseThrow(() -> new RuntimeException("CompanyRelationship não encontrado"));
		
		System.out.println(relation.getIdCompanyRelationship());
		// Check if a product with the same name already exists
	    ProductWarehouse existingProduct = productWarehouseRepository.findByProduct(product.getProduct());

	    ProductWarehouse productToCreate;
	    if (existingProduct != null) {
	        productToCreate = existingProduct;
	    } else {
	        productToCreate = productWarehouseMapper.toEntity(product);
	    }
	    
	    Integer qtd = productToCreate.getQuantity();
	    qtd += product.getQuantity();
	    productToCreate.setQuantity(qtd);
	    
		ProductWarehouse createdProduct = productWarehouseRepository.save(productToCreate);
		
		 // Crie a entrada na tabela de relação supplier_product
	    SupplierProduct supplierProduct = new SupplierProduct();
	    SupplierProduct.SupplierProductKey supplierProductKey = new SupplierProduct.SupplierProductKey();
	    supplierProductKey.setCompanyRelationshipId(relation.getIdCompanyRelationship());
	    supplierProductKey.setProductWarehouseId(createdProduct.getId());
	    supplierProduct.setId(supplierProductKey);
	    supplierProduct.setCompanyRelationship(relation);
	    supplierProduct.setProduct(createdProduct);
	    System.out.println(product.getSupplierPrice());
	    supplierProduct.setPrice(product.getSupplierPrice());

	    supplierProductRepository.save(supplierProduct);
	    
	    // Atualize o relacionamento bidirecional
	    relation.getSupplierProducts().add(supplierProduct);
	    companyRelationshipRepository.save(relation);
		
		return productWarehouseMapper.toResponseDTO(createdProduct);
	}
	
	public ProductWarehouseResponseDTO getProductById(UUID id) {
		ProductWarehouse product = productWarehouseRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com id: " + id));
		
		return productWarehouseMapper.toResponseDTO(product);
	}
	
	public ProductWarehouseResponseDTO updateProduct(UUID id, Map<String, Object> fields) {
		ProductWarehouse product = productWarehouseRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com id: " + id));
		
		fields.forEach((key, value) -> {
			Field field = ReflectionUtils.findField(ProductWarehouse.class, key);
			field.setAccessible(true);
			ReflectionUtils.setField(field, product, value);
		});
		
		productWarehouseRepository.save(product);
		
		return productWarehouseMapper.toResponseDTO(product);
	}
	
	public void deactivateProduct(UUID id) {
		ProductWarehouse product = productWarehouseRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com id: " + id));
		
		product.setActive(false);
		
		productWarehouseRepository.save(product);
	}
}
