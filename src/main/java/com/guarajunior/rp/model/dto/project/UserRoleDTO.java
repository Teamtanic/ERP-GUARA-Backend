package com.guarajunior.rp.model.dto.project;

import java.util.UUID;

import lombok.Data;

@Data
public class UserRoleDTO {
	private UUID userId;
    private String role;
}
