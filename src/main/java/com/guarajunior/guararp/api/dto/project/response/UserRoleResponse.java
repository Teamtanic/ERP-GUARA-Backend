package com.guarajunior.guararp.api.dto.project.response;

import com.guarajunior.guararp.infra.model.User;
import lombok.Data;

@Data
public class UserRoleResponse {
	private User user;
	private String role;
}
