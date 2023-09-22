package com.guarajunior.rp.model.dto.user;

import java.util.UUID;

import lombok.Data;

@Data
public class UserDTO {
	private String name;
	private String password;
	private String prontuary;
	private Integer courseId;
	private UUID roleId;
	private UUID departmentId;
}
