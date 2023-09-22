package com.guarajunior.rp.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import com.guarajunior.rp.model.ProductWarehouse;
import com.guarajunior.rp.model.dto.productwarehouse.ProductWarehouseDTO;
import com.guarajunior.rp.model.dto.productwarehouse.ProductWarehouseResponseDTO;

@Component
public class ProductWarehouseMapper {
private final ModelMapper modelMapper;
	
	public ProductWarehouseMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}
	
	public ProductWarehouseDTO toDTO(ProductWarehouse product) {
		return modelMapper.map(product, ProductWarehouseDTO.class);
	}
	
	public ProductWarehouse toEntity(ProductWarehouseDTO productDTO) {
		return modelMapper.map(productDTO, ProductWarehouse.class);
	}
	
	public ProductWarehouseResponseDTO toResponseDTO(ProductWarehouse product) {
		return modelMapper.map(product, ProductWarehouseResponseDTO.class);
	}
	
	public Page<ProductWarehouseResponseDTO> pageToResponsePageDTO(Page<ProductWarehouse> entityPage){
    	List<ProductWarehouseResponseDTO> entityList = entityPage
    											.getContent()
								    			.stream()
								                .map(this::toResponseDTO)
								                .collect(Collectors.toList());

    	return new PageImpl<>(entityList, entityPage.getPageable(), entityPage.getTotalElements());
    }
}
