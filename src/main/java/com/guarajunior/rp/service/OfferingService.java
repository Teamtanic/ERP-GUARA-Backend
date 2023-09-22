package com.guarajunior.rp.service;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.guarajunior.rp.enums.OfferingType;
import com.guarajunior.rp.exception.CompanyServiceException;
import com.guarajunior.rp.mapper.OfferingMapper;
import com.guarajunior.rp.model.Offering;
import com.guarajunior.rp.model.dto.offering.OfferingDTO;
import com.guarajunior.rp.model.dto.offering.OfferingResponseDTO;
import com.guarajunior.rp.repository.OfferingRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class OfferingService {
	@Autowired
	private OfferingRepository offeringRepository;
	@Autowired
	private OfferingMapper offeringMapper;
	
	public Page<OfferingResponseDTO> getAllOfferings(Integer page, Integer size){
		Pageable pageable = PageRequest.of(page, size);
    	Page<Offering> offerings = offeringRepository.findAll(pageable);
    	return offeringMapper.pageToResponsePageDTO(offerings);
	}
	
	public Page<OfferingResponseDTO> getAllServices(Integer page, Integer size){
		Pageable pageable = PageRequest.of(page, size);
    	Page<Offering> offerings = offeringRepository.findByOfferingType(OfferingType.SERVIÇO, pageable);
    	return offeringMapper.pageToResponsePageDTO(offerings);
	}
	
	public Page<OfferingResponseDTO> getAllProducts(Integer page, Integer size){
		Pageable pageable = PageRequest.of(page, size);
    	Page<Offering> offerings = offeringRepository.findByOfferingType(OfferingType.PRODUTO, pageable);
    	return offeringMapper.pageToResponsePageDTO(offerings);
	}
	
	public OfferingResponseDTO createOffering(OfferingDTO offeringDTO) {
		try {
			Offering offeringToCreate = offeringMapper.toEntity(offeringDTO);
			
			Offering createdOffering = offeringRepository.save(offeringToCreate);
			
			return offeringMapper.toResponseDTO(createdOffering);
			
		} catch (Exception e) {
			throw new CompanyServiceException("Erro ao criar oferta: " + e.getMessage());
		}
	}
	
	public OfferingResponseDTO getOfferingById(UUID id) {
		Offering offering = offeringRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("Oferta não encontrada com id: " + id));
	
		return offeringMapper.toResponseDTO(offering);
	}
	
	public OfferingResponseDTO updateOffering(UUID id, Map<String, Object> fields) {
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
