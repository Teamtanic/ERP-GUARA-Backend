package com.guarajunior.rp.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import com.guarajunior.rp.model.BankAccount;
import com.guarajunior.rp.model.dto.bankaccount.BankAccountDTO;
import com.guarajunior.rp.model.dto.bankaccount.BankAccountResponseDTO;

@Component
public class BankAccountMapper {
	private final ModelMapper modelMapper;

    public BankAccountMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    
    public BankAccount toEntity(BankAccountDTO bankAccountDTO) {
    	return modelMapper.map(bankAccountDTO, BankAccount.class);
    }
    
    public BankAccountDTO toDTO(BankAccount bankAccount) {
    	return modelMapper.map(bankAccount, BankAccountDTO.class);
    }
    
    public BankAccountResponseDTO toResponseDTO(BankAccount bankAccount) {
    	return modelMapper.map(bankAccount, BankAccountResponseDTO.class);
    }
    
    public Page<BankAccountResponseDTO> pageToResponsePageDTO(Page<BankAccount> entityPage){
    	List<BankAccountResponseDTO> entityList = entityPage
				.getContent()
    			.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());

    	return new PageImpl<>(entityList, entityPage.getPageable(), entityPage.getTotalElements());
    }
}
