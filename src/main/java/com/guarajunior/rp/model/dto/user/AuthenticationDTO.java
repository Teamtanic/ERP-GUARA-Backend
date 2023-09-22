package com.guarajunior.rp.model.dto.user;

import lombok.Data;

@Data
public class AuthenticationDTO {
	private String login;
	private String password;
}
