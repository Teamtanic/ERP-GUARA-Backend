package com.guarajunior.guararp.domain.mapper;

import com.guarajunior.guararp.infra.model.Offering;
import com.guarajunior.guararp.api.dto.offering.request.OfferingCreateRequest;
import com.guarajunior.guararp.api.dto.offering.response.OfferingResponse;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OfferingMapper {
	private final ModelMapper modelMapper;
	
	public OfferingMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}
	
	public Offering toEntity(OfferingCreateRequest offeringCreateRequest) {
		return modelMapper.map(offeringCreateRequest, Offering.class);
	}
	
	public OfferingCreateRequest toDTO(Offering offering) {
		return modelMapper.map(offering, OfferingCreateRequest.class);
	}
	
	public OfferingResponse toResponseDTO(Offering offering) {
		return modelMapper.map(offering, OfferingResponse.class);
	}
	
	public Page<OfferingResponse> pageToResponsePageDTO(Page<Offering> entityPage){
		List<OfferingResponse> entityList = entityPage
												.getContent()
												.stream()
												.map(this::toResponseDTO)
												.collect(Collectors.toList());
		
		return new PageImpl<>(entityList, entityPage.getPageable(), entityPage.getTotalElements());
	}
}
