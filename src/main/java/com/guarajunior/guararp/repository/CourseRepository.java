package com.guarajunior.guararp.repository;

import com.guarajunior.guararp.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Integer> {
	Page<Course> findAll(Pageable pageable);
}
