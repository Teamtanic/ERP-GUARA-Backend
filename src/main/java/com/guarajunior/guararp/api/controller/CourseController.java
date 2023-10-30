package com.guarajunior.guararp.api.controller;


import com.guarajunior.guararp.api.error.exception.CompanyServiceException;
import com.guarajunior.guararp.api.error.ErrorResponse;
import com.guarajunior.guararp.api.dto.course.request.CourseCreateRequest;
import com.guarajunior.guararp.api.dto.course.response.CourseResponse;
import com.guarajunior.guararp.domain.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/cursos")
public class CourseController {
	@Autowired
	private CourseService courseService;
	
	@GetMapping
	public ResponseEntity<?> list(@RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size){
		try {
			Page<CourseResponse> courses = courseService.getAllCourses(page, size);
			
			return ResponseEntity.status(HttpStatus.OK).body(courses);
		} catch(CompanyServiceException  e) {
			String errorMessage = "Erro ao listar cursos";
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).message(errorMessage).build());
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable Integer id) {
		try {
			CourseResponse course = courseService.getCourseById(id);
		
			return ResponseEntity.status(HttpStatus.OK).body(course);
		} catch(CompanyServiceException  e) {
			String errorMessage = "Erro ao resgatar curso";
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).message(errorMessage).build());
		}
	}
	
	@PatchMapping("/{id}")
	@Transactional
	public ResponseEntity<?> update(@Valid @PathVariable Integer id, @RequestBody Map<String, Object> fields) {
		try {
			CourseResponse updatedCourse = courseService.updateCourse(id, fields);
			
			return ResponseEntity.status(HttpStatus.OK).body(updatedCourse);
		} catch(CompanyServiceException  e) {
			String errorMessage = "Erro ao atualizar curso";
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).message(errorMessage).build());
		}
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<?> register(@Valid @RequestBody CourseCreateRequest courseCreateRequest){
		try {
			CourseResponse createdCourse = courseService.createCourse(courseCreateRequest);
			
			return ResponseEntity.status(HttpStatus.CREATED).body(createdCourse);
		} catch(CompanyServiceException  e) {
			String errorMessage = "Erro ao criar curso";
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).message(errorMessage).build());
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id){
		try {
			courseService.deleteCourse(id);
			return ResponseEntity.status(HttpStatus.OK).build();
		} catch(CompanyServiceException  e) {
			String errorMessage = "Erro ao deletar curso";
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).message(errorMessage).build());
		}
	}
}
