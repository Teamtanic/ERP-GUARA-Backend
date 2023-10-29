package com.guarajunior.guararp.mapper;

import com.guarajunior.guararp.model.Company;
import com.guarajunior.guararp.model.dto.company.CompanyCreateDTO;
import com.guarajunior.guararp.model.dto.company.CompanyResponseDTO;
import com.guarajunior.guararp.model.dto.company.CompanyUpdateDTO;
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

    public Company toEntity(CompanyCreateDTO companyCreateDTO) {
        return modelMapper.map(companyCreateDTO, Company.class);
    }

    public Company toEntity(CompanyUpdateDTO companyUpdateDTO) {
        return modelMapper.map(companyUpdateDTO, Company.class);
    }

    public CompanyUpdateDTO toUpdateDTO(Company company) {
        return modelMapper.map(company, CompanyUpdateDTO.class);
    }

    public CompanyResponseDTO toResponseDTO(Company company) {
        return modelMapper.map(company, CompanyResponseDTO.class);
    }
    
    public Page<CompanyResponseDTO> pageToResponsePageDTO(Page<Company> entityPage){
    	List<CompanyResponseDTO> entityList = entityPage
    											.getContent()
								    			.stream()
								                .map(this::toResponseDTO)
								                .collect(Collectors.toList());

    	return new PageImpl<>(entityList, entityPage.getPageable(), entityPage.getTotalElements());
    }
}
