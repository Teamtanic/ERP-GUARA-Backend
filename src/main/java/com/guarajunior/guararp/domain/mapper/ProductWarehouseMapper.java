package com.guarajunior.guararp.domain.mapper;

import com.guarajunior.guararp.infra.model.ProductWarehouse;
import com.guarajunior.guararp.api.dto.productwarehouse.request.ProductWarehouseCreateRequest;
import com.guarajunior.guararp.api.dto.productwarehouse.response.ProductWarehouseResponse;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductWarehouseMapper {
private final ModelMapper modelMapper;
	
	public ProductWarehouseMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}
	
	public ProductWarehouseCreateRequest toDTO(ProductWarehouse product) {
		return modelMapper.map(product, ProductWarehouseCreateRequest.class);
	}
	
	public ProductWarehouse toEntity(ProductWarehouseCreateRequest productDTO) {
		return modelMapper.map(productDTO, ProductWarehouse.class);
	}
	
	public ProductWarehouseResponse toResponseDTO(ProductWarehouse product) {
		return modelMapper.map(product, ProductWarehouseResponse.class);
	}
	
	public Page<ProductWarehouseResponse> pageToResponsePageDTO(Page<ProductWarehouse> entityPage){
    	List<ProductWarehouseResponse> entityList = entityPage
    											.getContent()
								    			.stream()
								                .map(this::toResponseDTO)
								                .collect(Collectors.toList());

    	return new PageImpl<>(entityList, entityPage.getPageable(), entityPage.getTotalElements());
    }
}
