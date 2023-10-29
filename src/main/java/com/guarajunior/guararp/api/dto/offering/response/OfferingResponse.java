package com.guarajunior.guararp.api.dto.offering.response;

import com.guarajunior.guararp.infra.enums.OfferingType;
import lombok.Data;

@Data
public class OfferingResponse {
	private String description;
	private OfferingType type;
}
