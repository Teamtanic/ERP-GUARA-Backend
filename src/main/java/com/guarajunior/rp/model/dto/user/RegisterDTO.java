package com.guarajunior.rp.model.dto.user;

import java.util.UUID;

import lombok.Data;

@Data
public class RegisterDTO {
	private String name;
	private String login;
	private String prontuary;
	private String password;
	private Integer courseId;
	private UUID roleId;
	private UUID departmentId;
}
