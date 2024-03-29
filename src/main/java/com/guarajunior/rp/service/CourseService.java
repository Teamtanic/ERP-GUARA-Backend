package com.guarajunior.rp.service;

import java.lang.reflect.Field;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.guarajunior.rp.exception.CompanyServiceException;
import com.guarajunior.rp.mapper.CourseMapper;
import com.guarajunior.rp.model.Course;
import com.guarajunior.rp.model.dto.course.CourseDTO;
import com.guarajunior.rp.model.dto.course.CourseResponseDTO;
import com.guarajunior.rp.repository.CourseRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CourseService {
	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private CourseMapper courseMapper;
	
	public Page<CourseResponseDTO> getAllCourses(Integer page, Integer size){
		Pageable pageable = PageRequest.of(page, size);
    	Page<Course> coursePage = courseRepository.findAll(pageable);
    	return courseMapper.pageToResponsePageDTO(coursePage);
	}
	
	public CourseResponseDTO createCourse(CourseDTO courseDTO) {
		try {
			Course courseToCreate = courseMapper.toEntity(courseDTO);
			Course createdCourse = courseRepository.save(courseToCreate);
			
			return courseMapper.toResponseDTO(createdCourse);
		} catch(Exception e) {
    		throw new CompanyServiceException("Erro ao criar empresa: " + e.getMessage());
    	}
	}
	
	public CourseResponseDTO getCourseById(Integer id) {
		Course course = courseRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Curso não encontrado com o ID: " + id));
	
		return courseMapper.toResponseDTO(course);
	}
	
	public CourseResponseDTO updateCourse(Integer id, Map<String, Object> fields) {
		Course course = courseRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Curso não encontrado"));
		
		fields.forEach((key, value) -> {
			Field field = ReflectionUtils.findField(Course.class, key);
			field.setAccessible(true);
			ReflectionUtils.setField(field, course, value);
		});
		
		courseRepository.save(course);
		
		return courseMapper.toResponseDTO(course);
	}
	
	public void deleteCourse(Integer id) {
		Course course = courseRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Curso não encontrado"));
		
		courseRepository.delete(course);
	}
}
