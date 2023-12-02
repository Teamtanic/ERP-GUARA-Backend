package com.guarajunior.guararp.api.dto.offering.response;

import com.guarajunior.guararp.infra.enums.OfferingType;
import lombok.Data;

import java.util.UUID;

@Data
public class OfferingResponse {
	private UUID id;
	private String description;
	private OfferingType type;
}
