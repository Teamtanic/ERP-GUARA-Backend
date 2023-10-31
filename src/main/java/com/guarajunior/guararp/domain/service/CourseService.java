package com.guarajunior.guararp.domain.service;

import com.guarajunior.guararp.api.dto.course.request.CourseCreateRequest;
import com.guarajunior.guararp.api.dto.course.response.CourseResponse;
import com.guarajunior.guararp.domain.mapper.CourseMapper;
import com.guarajunior.guararp.infra.model.Course;
import com.guarajunior.guararp.infra.repository.CourseRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    public CourseService(CourseRepository courseRepository, CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }

    public Page<CourseResponse> getAllCourses(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Course> coursePage = courseRepository.findAll(pageable);
        return courseMapper.pageToResponsePageDTO(coursePage);
    }

    public CourseResponse createCourse(CourseCreateRequest courseCreateRequest) {
        Course courseToCreate = courseMapper.toEntity(courseCreateRequest);
        Course createdCourse = courseRepository.save(courseToCreate);

        return courseMapper.toResponseDTO(createdCourse);
    }

    public CourseResponse getCourseById(Integer id) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Curso não encontrado com o ID: " + id));

        return courseMapper.toResponseDTO(course);
    }

    public CourseResponse updateCourse(Integer id, Map<String, Object> fields) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Curso não encontrado"));
        BeanUtils.copyProperties(fields, course, "id");

        return courseMapper.toResponseDTO(courseRepository.save(course));
    }

    public void deleteCourse(Integer id) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Curso não encontrado"));
        courseRepository.delete(course);
    }
}
