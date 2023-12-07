package com.guarajunior.guararp.api.controller;


import com.guarajunior.guararp.api.dto.course.request.CourseCreateRequest;
import com.guarajunior.guararp.api.dto.course.response.CourseResponse;
import com.guarajunior.guararp.api.dto.course.response.CourseWithUsersResponse;
import com.guarajunior.guararp.domain.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/cursos")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<Page<CourseResponse>> list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        return ResponseEntity.status(HttpStatus.OK).body(courseService.getAllCourses(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseWithUsersResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(courseService.getCourseByIdWithUsers(id));
    }

    @PatchMapping("/{id}")
    @Transactional
    public ResponseEntity<CourseResponse> update(@Valid @PathVariable Integer id, @RequestBody Map<String, Object> fields) {
        return ResponseEntity.status(HttpStatus.OK).body(courseService.updateCourse(id, fields));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<CourseResponse> register(@Valid @RequestBody CourseCreateRequest courseCreateRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.createCourse(courseCreateRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        courseService.deleteCourse(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
