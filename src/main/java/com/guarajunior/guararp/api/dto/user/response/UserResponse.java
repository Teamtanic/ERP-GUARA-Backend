package com.guarajunior.guararp.api.dto.user.response;

import com.guarajunior.guararp.api.dto.contact.request.ContactCreateRequest;
import com.guarajunior.guararp.api.dto.course.response.CourseResponse;
import com.guarajunior.guararp.api.dto.department.response.DepartmentResponse;
import lombok.Data;

import java.util.UUID;

@Data
public class UserResponse {
	private UUID id;
	private String name;
	private String prontuary;
	private Boolean status;
	private CourseResponse course;
	private DepartmentResponse department;
	private ContactCreateRequest contact;
}
