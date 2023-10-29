package com.guarajunior.guararp.api.dto.user.request;

import lombok.Data;

@Data
public class UserAuthenticationRequest {
	private String login;
	private String password;
}
