package com.guarajunior.rp.model.dto.offering;

import com.guarajunior.rp.enums.OfferingType;

import lombok.Data;

@Data
public class OfferingDTO {
	private String description;
	private OfferingType type;
}
