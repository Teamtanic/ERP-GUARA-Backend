package com.guarajunior.guararp.model.dto.user;

import lombok.Data;

@Data
public class AuthenticationDTO {
	private String login;
	private String password;
}
