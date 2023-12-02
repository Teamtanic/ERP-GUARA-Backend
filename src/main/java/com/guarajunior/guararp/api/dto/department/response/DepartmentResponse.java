package com.guarajunior.guararp.api.dto.department.response;

import lombok.Data;

import java.util.UUID;

@Data
public class DepartmentResponse {
	private UUID id;
	private String name;
}
