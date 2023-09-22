package com.guarajunior.rp.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import com.guarajunior.rp.model.Offering;
import com.guarajunior.rp.model.dto.offering.OfferingDTO;
import com.guarajunior.rp.model.dto.offering.OfferingResponseDTO;

@Component
public class OfferingMapper {
	private final ModelMapper modelMapper;
	
	public OfferingMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}
	
	public Offering toEntity(OfferingDTO offeringDTO) {
		return modelMapper.map(offeringDTO, Offering.class);
	}
	
	public OfferingDTO toDTO(Offering offering) {
		return modelMapper.map(offering, OfferingDTO.class);
	}
	
	public OfferingResponseDTO toResponseDTO(Offering offering) {
		return modelMapper.map(offering, OfferingResponseDTO.class);
	}
	
	public Page<OfferingResponseDTO> pageToResponsePageDTO(Page<Offering> entityPage){
		List<OfferingResponseDTO> entityList = entityPage
												.getContent()
												.stream()
												.map(this::toResponseDTO)
												.collect(Collectors.toList());
		
		return new PageImpl<>(entityList, entityPage.getPageable(), entityPage.getTotalElements());
	}
}
