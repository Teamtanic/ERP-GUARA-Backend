package com.guarajunior.rp.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import com.guarajunior.rp.model.Transaction;
import com.guarajunior.rp.model.dto.transaction.TransactionDTO;
import com.guarajunior.rp.model.dto.transaction.TransactionResponseDTO;

@Component
public class TransactionMapper {
	private final ModelMapper modelMapper;

    public TransactionMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    
    public Transaction toEntity(TransactionDTO transactionDTO) {
    	return modelMapper.map(transactionDTO, Transaction.class);
    }
    
    public TransactionDTO toDTO(Transaction transaction) {
    	return modelMapper.map(transaction, TransactionDTO.class);
    }
    
    public TransactionResponseDTO toResponseDTO(Transaction transaction) {
    	return modelMapper.map(transaction, TransactionResponseDTO.class);
    }
    
    public Page<TransactionResponseDTO> pageToResponsePageDTO(Page<Transaction> entityPage){
    	List<TransactionResponseDTO> entityList = entityPage
				.getContent()
    			.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());

    	return new PageImpl<>(entityList, entityPage.getPageable(), entityPage.getTotalElements());
    }
}
