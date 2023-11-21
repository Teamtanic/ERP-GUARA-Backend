package com.guarajunior.guararp.domain.service;

import com.guarajunior.guararp.api.dto.offering.request.OfferingCreateRequest;
import com.guarajunior.guararp.api.dto.offering.response.OfferingResponse;
import com.guarajunior.guararp.domain.mapper.OfferingMapper;
import com.guarajunior.guararp.infra.enums.OfferingType;
import com.guarajunior.guararp.infra.model.Offering;
import com.guarajunior.guararp.infra.repository.OfferingRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OfferingService {
    private final OfferingRepository offeringRepository;
    private final OfferingMapper offeringMapper;
    private final ModelMapper mapper;

    public Page<OfferingResponse> getAllOfferings(Integer page, Integer size, String type) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Offering> offeringPage;

        Map<String, OfferingType> typeMap = new HashMap<>();
        typeMap.put("servicos", OfferingType.SERVIÇO);
        typeMap.put("produtos", OfferingType.PRODUTO);

        if (type == null || !typeMap.containsKey(type)) {
            offeringPage = offeringRepository.findAll(pageable);
        } else {
            offeringPage = offeringRepository.findByOfferingType(typeMap.get(type), pageable);
        }

        return offeringMapper.pageToResponsePageDTO(offeringPage);
    }

    public OfferingResponse createOffering(OfferingCreateRequest offeringCreateRequest) {
        Offering offeringToCreate = offeringMapper.toEntity(offeringCreateRequest);
        offeringToCreate.setActive(true);
        Offering createdOffering = offeringRepository.save(offeringToCreate);

        return offeringMapper.toResponseDTO(createdOffering);

    }

    public OfferingResponse getOfferingById(UUID id) {
        Offering offering = offeringRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Oferta não encontrada com id: " + id));

        return offeringMapper.toResponseDTO(offering);
    }

    public OfferingResponse updateOffering(UUID id, OfferingCreateRequest offeringCreateRequest) {
        Offering offering = offeringRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Oferta não encontrada com id: " + id));

        mapper.map(offeringCreateRequest, offering);

        return offeringMapper.toResponseDTO(offeringRepository.save(offering));
    }


    public void deactivateOffering(UUID id) {
        Offering offering = offeringRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Oferta não encontrada com id: " + id));
        offering.setActive(false);

        offeringRepository.save(offering);
    }
}
