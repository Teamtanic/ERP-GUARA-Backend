package com.guarajunior.guararp.domain.mapper;

import com.guarajunior.guararp.infra.model.Company;
import com.guarajunior.guararp.api.dto.company.request.CompanyCreateRequest;
import com.guarajunior.guararp.api.dto.company.response.CompanyResponse;
import com.guarajunior.guararp.api.dto.company.request.CompanyUpdateRequest;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CompanyMapper {
    private final ModelMapper modelMapper;

    public CompanyMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Company toEntity(CompanyCreateRequest companyCreateRequest) {
        return modelMapper.map(companyCreateRequest, Company.class);
    }

    public Company toEntity(CompanyUpdateRequest companyUpdateRequest) {
        return modelMapper.map(companyUpdateRequest, Company.class);
    }

    public CompanyUpdateRequest toUpdateDTO(Company company) {
        return modelMapper.map(company, CompanyUpdateRequest.class);
    }

    public CompanyResponse toResponseDTO(Company company) {
        return modelMapper.map(company, CompanyResponse.class);
    }
    
    public Page<CompanyResponse> pageToResponsePageDTO(Page<Company> entityPage){
    	List<CompanyResponse> entityList = entityPage
    											.getContent()
								    			.stream()
								                .map(this::toResponseDTO)
								                .collect(Collectors.toList());

    	return new PageImpl<>(entityList, entityPage.getPageable(), entityPage.getTotalElements());
    }
}
