package com.guarajunior.guararp.domain.mapper;

import com.guarajunior.guararp.infra.model.Transaction;
import com.guarajunior.guararp.api.dto.transaction.request.TransactionCreateRequest;
import com.guarajunior.guararp.api.dto.transaction.response.TransactionResponse;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransactionMapper {
	private final ModelMapper modelMapper;

    public TransactionMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    
    public Transaction toEntity(TransactionCreateRequest transactionCreateRequest) {
    	return modelMapper.map(transactionCreateRequest, Transaction.class);
    }
    
    public TransactionCreateRequest toDTO(Transaction transaction) {
    	return modelMapper.map(transaction, TransactionCreateRequest.class);
    }
    
    public TransactionResponse toResponseDTO(Transaction transaction) {
    	return modelMapper.map(transaction, TransactionResponse.class);
    }
    
    public Page<TransactionResponse> pageToResponsePageDTO(Page<Transaction> entityPage){
    	List<TransactionResponse> entityList = entityPage
				.getContent()
    			.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());

    	return new PageImpl<>(entityList, entityPage.getPageable(), entityPage.getTotalElements());
    }
}
