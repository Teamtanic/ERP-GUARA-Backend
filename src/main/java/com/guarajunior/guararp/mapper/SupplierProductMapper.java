package com.guarajunior.guararp.mapper;

import com.guarajunior.guararp.model.SupplierProduct;
import com.guarajunior.guararp.model.dto.productwarehouse.ProductSupplierInfoDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

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
