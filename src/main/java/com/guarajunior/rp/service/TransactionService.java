package com.guarajunior.rp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.guarajunior.rp.mapper.TransactionMapper;
import com.guarajunior.rp.model.BankAccount;
import com.guarajunior.rp.model.ProductWarehouse;
import com.guarajunior.rp.model.Transaction;
import com.guarajunior.rp.model.dto.transaction.TransactionDTO;
import com.guarajunior.rp.model.dto.transaction.TransactionResponseDTO;
import com.guarajunior.rp.repository.BankAccountRepository;
import com.guarajunior.rp.repository.ProductWarehouseRepository;
import com.guarajunior.rp.repository.TransactionRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class TransactionService {
	@Autowired
	private TransactionRepository transactionRepository;
	@Autowired
	private TransactionMapper transactionMapper;
	@Autowired
	private ProductWarehouseRepository productWarehouseRepository;
	@Autowired
	private BankAccountRepository bankAccountRepository;
	
	public Page<TransactionResponseDTO> listAll(Integer page, Integer size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Transaction> transactionPage = transactionRepository.findAll(pageable);
		return transactionMapper.pageToResponsePageDTO(transactionPage);
	}
	
	@Transactional
	public TransactionResponseDTO createTransaction(@RequestBody TransactionDTO transactionCreateDTO) {
		Transaction transactionToCreate = transactionMapper.toEntity(transactionCreateDTO);
		
		UUID bankAccountId = transactionCreateDTO.getBankAccountId();
		BankAccount bankAccount = bankAccountRepository.findById(bankAccountId)
											.orElseThrow(() -> new EntityNotFoundException(
													"ProductWarehouse não encontrado com o ID: " 
													+ bankAccountId));
		
		transactionToCreate.setBankAccount(bankAccount);
		
		List<ProductWarehouse> productsWarehouse = new ArrayList<>();
		
		if(transactionCreateDTO.getProductWarehouseIds() != null) {
			for (UUID productWarehouseId : transactionCreateDTO.getProductWarehouseIds()) {
		        ProductWarehouse productWarehouse = productWarehouseRepository.findById(productWarehouseId)
		                .orElseThrow(() -> new EntityNotFoundException("ProductWarehouse não encontrado com o ID: " + productWarehouseId));
		        productsWarehouse.add(productWarehouse);
	    	}
		}
		
		transactionToCreate.setProductWarehouse(productsWarehouse);
		
		Transaction createdTransaction = transactionRepository.save(transactionToCreate);
		
		return transactionMapper.toResponseDTO(createdTransaction);
	}
	
	public TransactionResponseDTO getTransactionById(UUID id) {
		Transaction transaction = transactionRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Transação não encontrada com o ID: " + id));
		
		return transactionMapper.toResponseDTO(transaction);
	}
	
}
