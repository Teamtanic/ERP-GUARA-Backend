package com.guarajunior.rp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.guarajunior.rp.model.Course;

public interface CourseRepository extends JpaRepository<Course, Integer> {
	Page<Course> findAll(Pageable pageable);
}
