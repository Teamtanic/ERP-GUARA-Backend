package com.guarajunior.guararp.api.dto.project.response;

import com.guarajunior.guararp.infra.model.User;
import lombok.Data;

import java.util.UUID;

@Data
public class UserRoleResponse {
	private UUID id;
	private User user;
	private String role;
}
