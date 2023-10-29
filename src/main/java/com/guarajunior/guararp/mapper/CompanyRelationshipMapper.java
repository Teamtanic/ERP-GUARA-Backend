package com.guarajunior.guararp.mapper;

import com.guarajunior.guararp.model.CompanyRelationship;
import com.guarajunior.guararp.model.dto.companyrelationship.CompanyRelationshipDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class CompanyRelationshipMapper {
private final ModelMapper modelMapper;
	
	public CompanyRelationshipMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

	public CompanyRelationshipDTO toDTO(CompanyRelationship companyRelationship) {
        return modelMapper.map(companyRelationship, CompanyRelationshipDTO.class);
    }

    public CompanyRelationship toEntity(CompanyRelationshipDTO createDTO) {
        return modelMapper.map(createDTO, CompanyRelationship.class);
    }

    public void updateFromDTO(CompanyRelationshipDTO updateDTO, CompanyRelationship companyRelationship) {
        modelMapper.map(updateDTO, companyRelationship);
    }
    
    public List<CompanyRelationship> toEntityList(List<CompanyRelationshipDTO> dtoList) {
        return dtoList.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
    
    public List<CompanyRelationshipDTO> toDTOList(List<CompanyRelationship> entityList) {
    	return entityList.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
