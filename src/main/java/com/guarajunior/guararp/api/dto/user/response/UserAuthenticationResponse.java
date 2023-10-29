package com.guarajunior.guararp.api.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserAuthenticationResponse {
	private String token;
}
