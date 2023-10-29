package com.guarajunior.guararp.model.dto.project;

import com.guarajunior.guararp.model.User;
import lombok.Data;

@Data
public class UserRoleResponseDTO {
	private User user;
	private String role;
}
