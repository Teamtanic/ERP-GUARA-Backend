package com.guarajunior.rp.model.dto.project;

import com.guarajunior.rp.model.User;

import lombok.Data;

@Data
public class UserRoleResponseDTO {
	private User user;
	private String role;
}
