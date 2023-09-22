package com.guarajunior.rp.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import com.guarajunior.rp.enums.BusinessRelationshipType;
import com.guarajunior.rp.exception.CompanyServiceException;
import com.guarajunior.rp.exception.EntityNotFoundException;
import com.guarajunior.rp.mapper.CompanyMapper;
import com.guarajunior.rp.mapper.CompanyRelationshipMapper;
import com.guarajunior.rp.mapper.ContactMapper;

import com.guarajunior.rp.model.Company;
import com.guarajunior.rp.model.CompanyRelationship;
import com.guarajunior.rp.model.Contact;
import com.guarajunior.rp.model.dto.company.CompanyCreateDTO;
import com.guarajunior.rp.model.dto.company.CompanyResponseDTO;
import com.guarajunior.rp.model.dto.contact.ContactDTO;


import com.guarajunior.rp.repository.CompanyRelationshipRepository;
import com.guarajunior.rp.repository.CompanyRepository;
import com.guarajunior.rp.repository.ContactRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CompanyService {

	@Autowired
    private CompanyRepository companyRepository;
	@Autowired
	private CompanyRelationshipRepository companyRelationshipRepository;
	@Autowired
	private ContactRepository contactRepository;
	@Autowired
	private CompanyMapper companyMapper;
	@Autowired
	private CompanyRelationshipMapper companyRelationshipMapper;
	@Autowired
	private ContactMapper contactMapper;


    public Page<CompanyResponseDTO> getAllCompanies(Integer page, Integer size) {
    	Pageable pageable = PageRequest.of(page, size);
    	Page<Company> companyPage = companyRepository.findAll(pageable);
        return companyMapper.pageToResponsePageDTO(companyPage);
    }
    
    public Page<CompanyResponseDTO> getAllCustomers(Integer page, Integer size){
    	try {
    		Pageable pageable = PageRequest.of(page, size);
            Page<Company> customers = companyRepository.findByBusinessRelationshipType(BusinessRelationshipType.CLIENTE, pageable);
            return companyMapper.pageToResponsePageDTO(customers);
        } catch (Exception e) {
            throw new CompanyServiceException("Erro ao obter lista de clientes: " + e.getMessage());
        }
    }

    public Page<CompanyResponseDTO> getAllSuppliers(Integer page, Integer size){
    	try {
    		Pageable pageable = PageRequest.of(page, size);
    		Page<Company> suppliers = companyRepository.findByBusinessRelationshipType(BusinessRelationshipType.FORNECEDOR, pageable);
            return companyMapper.pageToResponsePageDTO(suppliers);
        } catch (Exception e) {
            throw new CompanyServiceException("Erro ao obter lista de fornecedores: " + e.getMessage());
        }
    }

    public CompanyResponseDTO getCompanyById(UUID id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empresa não encontrada com o ID: " + id));
    
        return companyMapper.toResponseDTO(company);
    }
  
    @Transactional
    public CompanyResponseDTO createCompany(CompanyCreateDTO createCompanyDTO) {
    	try {
	    	// Cria a empresa
	    	Company companyToCreate = companyMapper.toEntity(createCompanyDTO);
	    	Company createdCompany = companyRepository.save(companyToCreate);
	    	
	    	// Cria o relacionamento de cliente
	    	List<CompanyRelationship> companyRelationships = new ArrayList<>();
	    	List<String> relationshipTypes = createCompanyDTO.getBusinessRelationshipType();
	    	List<String> uniqueRelationshipTypes = relationshipTypes.stream().distinct().collect(Collectors.toList());
	        
	        for (String type : uniqueRelationshipTypes) {
	        	BusinessRelationshipType businessRelationshipType = BusinessRelationshipType.valueOf(type);
	        	CompanyRelationship relationship = new CompanyRelationship();
	        	relationship.setBusinessRelationship(businessRelationshipType);
	        	relationship.setCompany(createdCompany);
	            
	        	companyRelationshipRepository.save(relationship);
	            
	            companyRelationships.add(relationship);
	        }
	    	

	        CompanyResponseDTO responseCompany = companyMapper.toResponseDTO(createdCompany);
	        
	        responseCompany.setCompanyRelationships(companyRelationshipMapper.toDTOList(companyRelationships));
	
	        // Cria o contato
	        ContactDTO contactDTO = new ContactDTO();
	        contactDTO.setEmail(createCompanyDTO.getEmail());
	        contactDTO.setTelephone(createCompanyDTO.getTelephone());
	        contactDTO.setCell_phone(createCompanyDTO.getCell_phone());
	        createContact(createdCompany.getId(), contactDTO);
	        
	        responseCompany.setContact(Collections.singletonList(contactDTO));
	        
	        
	        return responseCompany;
    	} catch(Exception e) {
    		throw new CompanyServiceException("Erro ao criar empresa: " + e.getMessage());
    	}
    }
    
    @Transactional
    public void createContact(UUID companyId, ContactDTO contactCreateDTO) {
    	try {
    	companyRepository.findById(companyId)
                .orElseThrow(() -> new EntityNotFoundException("Empresa não encontrada com o ID: " + companyId));
    	
    	contactCreateDTO.setIdCompany(companyId);
    	
        Contact newContact = contactMapper.toEntity(contactCreateDTO);

        contactRepository.save(newContact);
    	} catch(Exception e) {
    		throw new CompanyServiceException("Erro ao criar empresa: " + e.getMessage());
    	}
    }

    public CompanyResponseDTO updateCompany(UUID id, Map<String, Object> fields) {
    	Company company = companyRepository.findById(id)
        		.orElseThrow(() -> new EntityNotFoundException("Empresa não encontrada com o ID: " + id));
        
        fields.forEach((key, value) -> {
        	Field field = ReflectionUtils.findField(Company.class, key);
        	field.setAccessible(true);
        	ReflectionUtils.setField(field, company, value);
        });
        
        companyRepository.save(company);
        
        return companyMapper.toResponseDTO(company);
    }

    public void deactivateCompanyRelationship(UUID idRelacao) {
    	CompanyRelationship companyRelationship = companyRelationshipRepository.findById(idRelacao)
    			.orElseThrow(() -> new RuntimeException("Relacionamento de empresa não encontrado"));

            companyRelationship.setActive(false);
            companyRelationshipRepository.save(companyRelationship);
    } 
    
    public void deactivateCompanyRelationships(UUID companyId) {
    	Company company = companyRepository.findById(companyId)
    			.orElseThrow(() -> new RuntimeException("Empresa não encontrada"));

            Optional<CompanyRelationship> customerRelationshipOptional  =
                    companyRelationshipRepository.findByCompanyAndIdBusinessRelationship(company, BusinessRelationshipType.CLIENTE);

            customerRelationshipOptional.ifPresent(customerRelationship -> {
                customerRelationship.setActive(false);
                companyRelationshipRepository.save(customerRelationship);
            });
            
            Optional<CompanyRelationship> supplierRelationshipOptional =
                    companyRelationshipRepository.findByCompanyAndIdBusinessRelationship(company, BusinessRelationshipType.FORNECEDOR); 
            
            supplierRelationshipOptional.ifPresent(supplierRelationship -> {
                supplierRelationship.setActive(false);
                companyRelationshipRepository.save(supplierRelationship);
            });
            
            if (!customerRelationshipOptional.isPresent() && !supplierRelationshipOptional.isPresent()) {
                throw new EntityNotFoundException("Relações de cliente e fornecedor não encontradas para a empresa com o ID: " + companyId);
            }
    }
    
    
}
