package com.guarajunior.guararp.model.dto.user;

import lombok.Data;

import java.util.UUID;

@Data
public class RegisterDTO {
	private String name;
	private String login;
	private String prontuary;
	private String password;
	private String email;
    private String telephone;
    private String cell_phone;
	private Integer courseId;
	private UUID roleId;
	private UUID departmentId;
}