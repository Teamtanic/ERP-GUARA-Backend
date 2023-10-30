package com.guarajunior.guararp.api.controller;


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
    public ResponseEntity<?> list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        Page<CourseResponse> courses = courseService.getAllCourses(page, size);

        return ResponseEntity.status(HttpStatus.OK).body(courses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        CourseResponse course = courseService.getCourseById(id);

        return ResponseEntity.status(HttpStatus.OK).body(course);
    }

    @PatchMapping("/{id}")
    @Transactional
    public ResponseEntity<?> update(@Valid @PathVariable Integer id, @RequestBody Map<String, Object> fields) {
        CourseResponse updatedCourse = courseService.updateCourse(id, fields);

        return ResponseEntity.status(HttpStatus.OK).body(updatedCourse);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> register(@Valid @RequestBody CourseCreateRequest courseCreateRequest) {
        CourseResponse createdCourse = courseService.createCourse(courseCreateRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdCourse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        courseService.deleteCourse(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
