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

import com.guarajunior.rp.exception.InsufficientProductException;
import com.guarajunior.rp.mapper.TransactionMapper;
import com.guarajunior.rp.model.BankAccount;
import com.guarajunior.rp.model.ProductWarehouse;
import com.guarajunior.rp.model.Transaction;
import com.guarajunior.rp.model.TransactionProduct;
import com.guarajunior.rp.model.dto.productwarehouse.ProductTransactionDTO;
import com.guarajunior.rp.model.dto.transaction.TransactionDTO;
import com.guarajunior.rp.model.dto.transaction.TransactionResponseDTO;
import com.guarajunior.rp.repository.BankAccountRepository;
import com.guarajunior.rp.repository.ProductWarehouseRepository;
import com.guarajunior.rp.repository.TransactionProductRepository;
import com.guarajunior.rp.repository.TransactionRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class TransactionService {
	@Autowired
	private TransactionRepository transactionRepository;
	@Autowired
	private ProductWarehouseRepository productWarehouseRepository;
	@Autowired
	private BankAccountRepository bankAccountRepository;
	@Autowired
	private TransactionProductRepository transactionProductRepository;
	@Autowired
	private TransactionMapper transactionMapper;
	
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
		
		Transaction createdTransaction = transactionRepository.save(transactionToCreate);
		
		List<TransactionProduct> transactionProducts = new ArrayList<>();
		
		if(transactionCreateDTO.getProductsWarehouse() != null) {
			List<ProductTransactionDTO> productDTOs = transactionCreateDTO.getProductsWarehouse();
			
			for (ProductTransactionDTO productDTO : productDTOs) {
				UUID productId = productDTO.getProductId();
	            Integer quantity = productDTO.getQuantity();
	            ProductWarehouse productWarehouse = productWarehouseRepository.findById(productId)
	            		.orElseThrow(() -> new EntityNotFoundException("ProductWarehouse não encontrado com o ID: " + productId));
	            
	            Integer productQuantity = productWarehouse.getQuantity();
	            productQuantity = productQuantity - quantity;
	            
	            if(productQuantity < quantity) {
	            	throw new InsufficientProductException("Produtos insuficientes no estoque: " + productWarehouse.getProduct());
	            }
	            
	            TransactionProduct transactionProduct = new TransactionProduct();
	            TransactionProduct.TransactionProductKey transactionProductKey = new TransactionProduct.TransactionProductKey();
	            transactionProductKey.setIdTransaction(createdTransaction.getId());
	            transactionProductKey.setProductWarehouseId(productId);
	            transactionProduct.setId(transactionProductKey);
	            transactionProduct.setProductWarehouse(productWarehouse);
	            transactionProduct.setQuantity(quantity);
	            transactionProduct.setTransaction(createdTransaction);
	            
	            transactionProductRepository.save(transactionProduct);
	            
	            productWarehouse.setQuantity(productQuantity);
	            productWarehouseRepository.save(productWarehouse);
	            
	            transactionProducts.add(transactionProduct);
			}
		}
		
		transactionToCreate.setProducts(transactionProducts);
		
		
		
		return transactionMapper.toResponseDTO(createdTransaction);
	}
	
	public TransactionResponseDTO getTransactionById(UUID id) {
		Transaction transaction = transactionRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Transação não encontrada com o ID: " + id));
		
		return transactionMapper.toResponseDTO(transaction);
	}
	
}
