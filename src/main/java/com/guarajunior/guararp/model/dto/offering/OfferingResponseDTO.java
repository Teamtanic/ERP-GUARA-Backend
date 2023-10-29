package com.guarajunior.guararp.model.dto.offering;

import com.guarajunior.guararp.enums.OfferingType;
import lombok.Data;

@Data
public class OfferingResponseDTO {
	private String description;
	private OfferingType type;
}
