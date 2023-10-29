package com.guarajunior.guararp.api.dto.offering.request;

import com.guarajunior.guararp.infra.enums.OfferingType;
import lombok.Data;

@Data
public class OfferingCreateRequest {
	private String description;
	private OfferingType type;
}
