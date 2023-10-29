package com.guarajunior.guararp.domain.mapper;

import com.guarajunior.guararp.infra.model.Course;
import com.guarajunior.guararp.api.dto.course.request.CourseCreateRequest;
import com.guarajunior.guararp.api.dto.course.response.CourseResponse;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CourseMapper {
	 private final ModelMapper modelMapper;
	 
	 public CourseMapper(ModelMapper modelMapper) {
		 this.modelMapper = modelMapper;
	 }
	 
	 public Course toEntity(CourseCreateRequest courseCreateRequest) {
		 return modelMapper.map(courseCreateRequest, Course.class);
	 }
	 
	 public CourseCreateRequest toDTO(Course course) {
		 return modelMapper.map(course, CourseCreateRequest.class);
	 }
	 
	 public CourseResponse toResponseDTO(Course course) {
		 return modelMapper.map(course, CourseResponse.class);
	 }
	 
	 public Page<CourseResponse> pageToResponsePageDTO(Page<Course> entityPage){
		 List<CourseResponse> entityList = entityPage
				 								.getContent()
				 								.stream()
				 								.map(this::toResponseDTO)
				 								.collect(Collectors.toList());
		 
		 return new PageImpl<>(entityList, entityPage.getPageable(), entityPage.getTotalElements());
	 }
}
