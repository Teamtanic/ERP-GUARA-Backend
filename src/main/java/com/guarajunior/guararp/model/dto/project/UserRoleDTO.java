package com.guarajunior.guararp.model.dto.project;

import lombok.Data;

import java.util.UUID;

@Data
public class UserRoleDTO {
	private UUID userId;
    private String role;
}
