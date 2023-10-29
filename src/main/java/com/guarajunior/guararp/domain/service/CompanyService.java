package com.guarajunior.guararp.domain.service;

import com.guarajunior.guararp.domain.mapper.CompanyRelationshipMapper;
import com.guarajunior.guararp.infra.enums.BusinessRelationshipType;
import com.guarajunior.guararp.api.error.exception.CompanyServiceException;
import com.guarajunior.guararp.api.error.exception.EntityNotFoundException;
import com.guarajunior.guararp.domain.mapper.CompanyMapper;
import com.guarajunior.guararp.infra.model.Company;
import com.guarajunior.guararp.infra.model.CompanyRelationship;
import com.guarajunior.guararp.api.dto.company.request.CompanyCreateRequest;
import com.guarajunior.guararp.api.dto.company.response.CompanyResponse;
import com.guarajunior.guararp.api.dto.contact.request.ContactCreateRequest;
import com.guarajunior.guararp.infra.repository.CompanyRelationshipRepository;
import com.guarajunior.guararp.infra.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CompanyService {

	@Autowired
    private CompanyRepository companyRepository;
	@Autowired
	private CompanyRelationshipRepository companyRelationshipRepository;
	@Autowired
	private CompanyMapper companyMapper;
	@Autowired
	private CompanyRelationshipMapper companyRelationshipMapper;
	@Autowired
	private ContactService contactService;


    public Page<CompanyResponse> getAllCompanies(Integer page, Integer size) {
    	Pageable pageable = PageRequest.of(page, size);
    	Page<Company> companyPage = companyRepository.findAll(pageable);
        return companyMapper.pageToResponsePageDTO(companyPage);
    }
    
    public Page<CompanyResponse> getAllCustomers(Integer page, Integer size){
    	try {
    		Pageable pageable = PageRequest.of(page, size);
            Page<Company> customers = companyRepository.findByBusinessRelationshipType(BusinessRelationshipType.CLIENTE, pageable);
            return companyMapper.pageToResponsePageDTO(customers);
        } catch (Exception e) {
            throw new CompanyServiceException("Erro ao obter lista de clientes: " + e.getMessage());
        }
    }

    public Page<CompanyResponse> getAllSuppliers(Integer page, Integer size){
    	try {
    		Pageable pageable = PageRequest.of(page, size);
    		Page<Company> suppliers = companyRepository.findByBusinessRelationshipType(BusinessRelationshipType.FORNECEDOR, pageable);
            return companyMapper.pageToResponsePageDTO(suppliers);
        } catch (Exception e) {
            throw new CompanyServiceException("Erro ao obter lista de fornecedores: " + e.getMessage());
        }
    }

    public CompanyResponse getCompanyById(UUID id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empresa não encontrada com o ID: " + id));
    
        return companyMapper.toResponseDTO(company);
    }
  
    @Transactional
    public CompanyResponse createCompany(CompanyCreateRequest createCompanyDTO) {
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
	    	

	        CompanyResponse responseCompany = companyMapper.toResponseDTO(createdCompany);
	        
	        responseCompany.setCompanyRelationships(companyRelationshipMapper.toDTOList(companyRelationships));
	
	        // Cria o contato
	        ContactCreateRequest contactCreateRequest = new ContactCreateRequest();
	        contactCreateRequest.setEmail(createCompanyDTO.getEmail());
	        contactCreateRequest.setTelephone(createCompanyDTO.getTelephone());
	        contactCreateRequest.setCell_phone(createCompanyDTO.getCell_phone());
	        contactCreateRequest.setCompany(createdCompany);
	        contactService.createContact(contactCreateRequest);
	        
	        responseCompany.setContact(contactCreateRequest);
	      
	        return responseCompany;
    	} catch(Exception e) {
    		throw new CompanyServiceException("Erro ao criar empresa: " + e.getMessage());
    	}
    }

    public CompanyResponse updateCompany(UUID id, Map<String, Object> fields) {
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
