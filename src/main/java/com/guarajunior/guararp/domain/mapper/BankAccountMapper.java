package com.guarajunior.guararp.domain.mapper;

import com.guarajunior.guararp.infra.model.BankAccount;
import com.guarajunior.guararp.api.dto.bankaccount.request.BankAccountCreateRequest;
import com.guarajunior.guararp.api.dto.bankaccount.response.BankAccountResponse;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BankAccountMapper {
	private final ModelMapper modelMapper;

    public BankAccountMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    
    public BankAccount toEntity(BankAccountCreateRequest bankAccountCreateRequest) {
    	return modelMapper.map(bankAccountCreateRequest, BankAccount.class);
    }
    
    public BankAccountCreateRequest toDTO(BankAccount bankAccount) {
    	return modelMapper.map(bankAccount, BankAccountCreateRequest.class);
    }
    
    public BankAccountResponse toResponseDTO(BankAccount bankAccount) {
    	return modelMapper.map(bankAccount, BankAccountResponse.class);
    }
    
    public Page<BankAccountResponse> pageToResponsePageDTO(Page<BankAccount> entityPage){
    	List<BankAccountResponse> entityList = entityPage
				.getContent()
    			.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());

    	return new PageImpl<>(entityList, entityPage.getPageable(), entityPage.getTotalElements());
    }
}
