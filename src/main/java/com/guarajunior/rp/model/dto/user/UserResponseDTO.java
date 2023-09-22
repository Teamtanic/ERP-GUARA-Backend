package com.guarajunior.rp.model.dto.user;

import com.guarajunior.rp.model.dto.course.CourseResponseDTO;

import lombok.Data;

@Data
public class UserResponseDTO {
	private String name;
	private String prontuary;
	private Boolean status;
	private CourseResponseDTO course;
}
