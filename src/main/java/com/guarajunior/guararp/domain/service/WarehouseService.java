package com.guarajunior.guararp.domain.service;

import com.guarajunior.guararp.api.dto.productwarehouse.request.ProductWarehouseCreateRequest;
import com.guarajunior.guararp.api.dto.productwarehouse.response.ProductWarehouseResponse;
import com.guarajunior.guararp.api.error.exception.EntityNotFoundException;
import com.guarajunior.guararp.domain.mapper.ProductWarehouseMapper;
import com.guarajunior.guararp.infra.model.CompanyRelationship;
import com.guarajunior.guararp.infra.model.ProductWarehouse;
import com.guarajunior.guararp.infra.model.SupplierProduct;
import com.guarajunior.guararp.infra.repository.CompanyRelationshipRepository;
import com.guarajunior.guararp.infra.repository.ProductWarehouseRepository;
import com.guarajunior.guararp.infra.repository.SupplierProductRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RequestBody;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.UUID;


@Service
public class WarehouseService {
    private final ProductWarehouseRepository productWarehouseRepository;
    private final CompanyRelationshipRepository companyRelationshipRepository;
    private final SupplierProductRepository supplierProductRepository;
    private final ProductWarehouseMapper productWarehouseMapper;

    public WarehouseService(ProductWarehouseRepository productWarehouseRepository, CompanyRelationshipRepository companyRelationshipRepository, SupplierProductRepository supplierProductRepository, ProductWarehouseMapper productWarehouseMapper) {
        this.productWarehouseRepository = productWarehouseRepository;
        this.companyRelationshipRepository = companyRelationshipRepository;
        this.supplierProductRepository = supplierProductRepository;
        this.productWarehouseMapper = productWarehouseMapper;
    }

    public Page<ProductWarehouseResponse> getAllItems(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductWarehouse> productPage = productWarehouseRepository.findAll(pageable);
        return productWarehouseMapper.pageToResponsePageDTO(productPage);
    }
    public Page<ProductWarehouseResponse> getAllItemsByCompany(UUID idCompany,Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductWarehouse> productPage = productWarehouseRepository.findAllByCompanyId(idCompany, pageable);
            return productWarehouseMapper.pageToResponsePageDTO(productPage);
    }

    @Transactional
    public ProductWarehouseResponse createProduct(@Valid @RequestBody ProductWarehouseCreateRequest product) {
        CompanyRelationship relation = companyRelationshipRepository.findById(product.getCompanyRelationship()).orElseThrow(() -> new RuntimeException("CompanyRelationship não encontrado"));

        System.out.println(relation.getIdCompanyRelationship());
        ProductWarehouse existingProduct = productWarehouseRepository.findByProduct(product.getProduct());

        ProductWarehouse productToCreate;
        if (existingProduct != null) {
            productToCreate = existingProduct;
        } else {
            productToCreate = productWarehouseMapper.toEntity(product);
        }

        Integer qtd = productToCreate.getQuantity();
        productToCreate.setQuantity(qtd);
        
        productToCreate.setActive(true);

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
    
    public Page<ProductWarehouseResponse> searchProductsByProduto(String product, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductWarehouse> productPage = productWarehouseRepository.findByProductContaining(product, pageable);
        return productWarehouseMapper.pageToResponsePageDTO(productPage);
    }

    public ProductWarehouseResponse getProductById(UUID id) {
        ProductWarehouse product = productWarehouseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com id: " + id));

        return productWarehouseMapper.toResponseDTO(product);
    }

    public ProductWarehouseResponse updateProduct(UUID id, Map<String, Object> fields) {
        ProductWarehouse product = productWarehouseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com id: " + id));

        fields.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(ProductWarehouse.class, key);
            field.setAccessible(true);
            ReflectionUtils.setField(field, product, value);
        });

        return productWarehouseMapper.toResponseDTO(productWarehouseRepository.save(product));
    }

    public void deactivateProduct(UUID id) {
        ProductWarehouse product = productWarehouseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com id: " + id));
        product.setActive(false);

        productWarehouseRepository.save(product);
    }
}
