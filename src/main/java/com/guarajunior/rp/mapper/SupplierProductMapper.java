package com.guarajunior.rp.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.guarajunior.rp.model.SupplierProduct;
import com.guarajunior.rp.model.dto.productwarehouse.ProductSupplierInfoDTO;

@Component
public class SupplierProductMapper {
	private final ModelMapper modelMapper;
	
	public SupplierProductMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}
	
	public SupplierProduct toEntity(ProductSupplierInfoDTO productSupplierInfo) {
		return modelMapper.map(productSupplierInfo, SupplierProduct.class);
	}
	
	public ProductSupplierInfoDTO toDTO(SupplierProduct supplierProduct) {
		return modelMapper.map(supplierProduct, ProductSupplierInfoDTO.class);
	}
}
