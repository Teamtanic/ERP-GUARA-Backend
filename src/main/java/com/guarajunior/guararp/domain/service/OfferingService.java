package com.guarajunior.guararp.domain.service;

import com.guarajunior.guararp.infra.enums.OfferingType;
import com.guarajunior.guararp.api.error.exception.CompanyServiceException;
import com.guarajunior.guararp.domain.mapper.OfferingMapper;
import com.guarajunior.guararp.infra.model.Offering;
import com.guarajunior.guararp.api.dto.offering.request.OfferingCreateRequest;
import com.guarajunior.guararp.api.dto.offering.response.OfferingResponse;
import com.guarajunior.guararp.infra.repository.OfferingRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class OfferingService {
	@Autowired
	private OfferingRepository offeringRepository;
	@Autowired
	private OfferingMapper offeringMapper;
	
	public Page<OfferingResponse> getAllOfferings(Integer page, Integer size){
		Pageable pageable = PageRequest.of(page, size);
    	Page<Offering> offerings = offeringRepository.findAll(pageable);
    	return offeringMapper.pageToResponsePageDTO(offerings);
	}
	
	public Page<OfferingResponse> getAllServices(Integer page, Integer size){
		Pageable pageable = PageRequest.of(page, size);
    	Page<Offering> offerings = offeringRepository.findByOfferingType(OfferingType.SERVIÇO, pageable);
    	return offeringMapper.pageToResponsePageDTO(offerings);
	}
	
	public Page<OfferingResponse> getAllProducts(Integer page, Integer size){
		Pageable pageable = PageRequest.of(page, size);
    	Page<Offering> offerings = offeringRepository.findByOfferingType(OfferingType.PRODUTO, pageable);
    	return offeringMapper.pageToResponsePageDTO(offerings);
	}
	
	public OfferingResponse createOffering(OfferingCreateRequest offeringCreateRequest) {
		try {
			Offering offeringToCreate = offeringMapper.toEntity(offeringCreateRequest);
			
			Offering createdOffering = offeringRepository.save(offeringToCreate);
			
			return offeringMapper.toResponseDTO(createdOffering);
			
		} catch (Exception e) {
			throw new CompanyServiceException("Erro ao criar oferta: " + e.getMessage());
		}
	}
	
	public OfferingResponse getOfferingById(UUID id) {
		Offering offering = offeringRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("Oferta não encontrada com id: " + id));
	
		return offeringMapper.toResponseDTO(offering);
	}
	
	public OfferingResponse updateOffering(UUID id, Map<String, Object> fields) {
		Offering offering = offeringRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Oferta não encontrada com id: " + id));
		
	    List<String> nonUpdatableFields = Arrays.asList("id");

		fields.forEach((key, value) -> {
			if (!nonUpdatableFields.contains(key)) {
				Field field = ReflectionUtils.findField(Offering.class, key);
				field.setAccessible(true);
				ReflectionUtils.setField(field, offering, value);
			}
		});
		
		offeringRepository.save(offering);
		
		return offeringMapper.toResponseDTO(offering);
	}
	
	
	public void deactivateOffering(UUID id) {
		Offering offering = offeringRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Oferta não encontrada com id: " + id));
		
		offering.setActive(false);
		
		offeringRepository.save(offering);
	}
}
