package com.guarajunior.guararp.domain.mapper;

import com.guarajunior.guararp.infra.model.CompanyRelationship;
import com.guarajunior.guararp.api.dto.companyrelationship.response.CompanyRelationshipResponse;
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

	public CompanyRelationshipResponse toDTO(CompanyRelationship companyRelationship) {
        return modelMapper.map(companyRelationship, CompanyRelationshipResponse.class);
    }

    public CompanyRelationship toEntity(CompanyRelationshipResponse createDTO) {
        return modelMapper.map(createDTO, CompanyRelationship.class);
    }

    public void updateFromDTO(CompanyRelationshipResponse updateDTO, CompanyRelationship companyRelationship) {
        modelMapper.map(updateDTO, companyRelationship);
    }
    
    public List<CompanyRelationship> toEntityList(List<CompanyRelationshipResponse> dtoList) {
        return dtoList.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
    
    public List<CompanyRelationshipResponse> toDTOList(List<CompanyRelationship> entityList) {
    	return entityList.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
