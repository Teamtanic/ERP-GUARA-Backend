package com.guarajunior.guararp.api.dto.user.request;

import lombok.Data;

import java.util.UUID;

@Data
public class UserUpdateRequest {
	private String name;
	private String password;
	private String prontuary;
	private Integer courseId;
	private UUID roleId;
	private UUID departmentId;
}
