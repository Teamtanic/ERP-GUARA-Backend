package com.guarajunior.guararp.domain.mapper;

import com.guarajunior.guararp.infra.model.SupplierProduct;
import com.guarajunior.guararp.api.dto.productwarehouse.response.ProductSupplierInfoResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class SupplierProductMapper {
	private final ModelMapper modelMapper;
	
	public SupplierProductMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}
	
	public SupplierProduct toEntity(ProductSupplierInfoResponse productSupplierInfo) {
		return modelMapper.map(productSupplierInfo, SupplierProduct.class);
	}
	
	public ProductSupplierInfoResponse toDTO(SupplierProduct supplierProduct) {
		return modelMapper.map(supplierProduct, ProductSupplierInfoResponse.class);
	}
}
