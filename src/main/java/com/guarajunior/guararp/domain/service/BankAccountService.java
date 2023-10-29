package com.guarajunior.guararp.domain.service;

import com.guarajunior.guararp.domain.mapper.BankAccountMapper;
import com.guarajunior.guararp.infra.model.BankAccount;
import com.guarajunior.guararp.api.dto.bankaccount.request.BankAccountCreateRequest;
import com.guarajunior.guararp.api.dto.bankaccount.response.BankAccountResponse;
import com.guarajunior.guararp.infra.repository.BankAccountRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RequestBody;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class BankAccountService {
	@Autowired
	private BankAccountRepository bankAccountRepository;
	@Autowired
	private BankAccountMapper bankAccountMapper;
	
	public Page<BankAccountResponse> listAll(Integer page, Integer size){
		Pageable pageable = PageRequest.of(page, size);
		Page<BankAccount> bankAccountPage = bankAccountRepository.findAll(pageable);
		return bankAccountMapper.pageToResponsePageDTO(bankAccountPage);
	}
	
	public BankAccountResponse createAccount(@RequestBody BankAccountCreateRequest bankAccountCreateRequest) {
		BankAccount bankAccountToCreate = bankAccountMapper.toEntity(bankAccountCreateRequest);
		
		BankAccount createdBankAccount = bankAccountRepository.save(bankAccountToCreate);
		
		return bankAccountMapper.toResponseDTO(createdBankAccount);
	}
	
	public BankAccountResponse getBankAccountById(UUID id) {
		BankAccount bankAccount = bankAccountRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Conta no banco não encontrada com id: " + id));
	
		return bankAccountMapper.toResponseDTO(bankAccount);
	}
	
	public BankAccountResponse updateBankAccount(UUID id, Map<String, Object> fields) {
		BankAccount bankAccount = bankAccountRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Conta no banco não encontrada com id: " + id));
		
		List<String> nonUpdatableFields = Arrays.asList("id");
		
		fields.forEach((key, value) -> {
			if (!nonUpdatableFields.contains(key)) {
				Field field = ReflectionUtils.findField(BankAccount.class, key);
				field.setAccessible(true);
				if (field.getType().equals(BigDecimal.class)) {
		            BigDecimal newValue = new BigDecimal(value.toString());
		            ReflectionUtils.setField(field, bankAccount, newValue);
		        } else {
		            ReflectionUtils.setField(field, bankAccount, value);
		        }
			}
		});
		
		bankAccountRepository.save(bankAccount);
		
		return bankAccountMapper.toResponseDTO(bankAccount);
	}
	
	public void deactivateBankAccount(UUID id) {
		BankAccount bankAccount = bankAccountRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Conta no banco não encontrada com id: " + id));
		
		bankAccount.setActive(false);
		
		bankAccountRepository.save(bankAccount);
	}
}
