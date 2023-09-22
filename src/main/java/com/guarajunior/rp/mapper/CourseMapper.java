package com.guarajunior.rp.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import com.guarajunior.rp.model.Course;
import com.guarajunior.rp.model.dto.course.CourseDTO;
import com.guarajunior.rp.model.dto.course.CourseResponseDTO;

@Component
public class CourseMapper {
	 private final ModelMapper modelMapper;
	 
	 public CourseMapper(ModelMapper modelMapper) {
		 this.modelMapper = modelMapper;
	 }
	 
	 public Course toEntity(CourseDTO courseDTO) {
		 return modelMapper.map(courseDTO, Course.class);
	 }
	 
	 public CourseDTO toDTO(Course course) {
		 return modelMapper.map(course, CourseDTO.class);
	 }
	 
	 public CourseResponseDTO toResponseDTO(Course course) {
		 return modelMapper.map(course, CourseResponseDTO.class);
	 }
	 
	 public Page<CourseResponseDTO> pageToResponsePageDTO(Page<Course> entityPage){
		 List<CourseResponseDTO> entityList = entityPage
				 								.getContent()
				 								.stream()
				 								.map(this::toResponseDTO)
				 								.collect(Collectors.toList());
		 
		 return new PageImpl<>(entityList, entityPage.getPageable(), entityPage.getTotalElements());
	 }
}
