package com.guarajunior.guararp.mapper;

import com.guarajunior.guararp.model.Transaction;
import com.guarajunior.guararp.model.dto.transaction.TransactionDTO;
import com.guarajunior.guararp.model.dto.transaction.TransactionResponseDTO;
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
