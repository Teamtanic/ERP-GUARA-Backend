package com.guarajunior.guararp.model.dto.user;

import com.guarajunior.guararp.model.dto.contact.ContactDTO;
import com.guarajunior.guararp.model.dto.course.CourseResponseDTO;
import lombok.Data;

@Data
public class UserResponseDTO {
	private String name;
	private String prontuary;
	private Boolean status;
	private CourseResponseDTO course;
	private ContactDTO contact;
}
