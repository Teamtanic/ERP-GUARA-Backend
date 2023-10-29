package com.guarajunior.guararp.model.dto.user;

import lombok.Data;

import java.util.UUID;

@Data
public class UserDTO {
	private String name;
	private String password;
	private String prontuary;
	private Integer courseId;
	private UUID roleId;
	private UUID departmentId;
}
