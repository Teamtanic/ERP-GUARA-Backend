package com.guarajunior.guararp.infra.repository;

import com.guarajunior.guararp.infra.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Integer> {
	Page<Course> findAll(Pageable pageable);

	@Query("SELECT c FROM Course c LEFT JOIN FETCH c.users WHERE c.id = :courseId")
	Optional<Course> findByIdWithUsers(@Param("courseId") Integer courseId);
	
	Page<Course> findByNameContaining(String name, Pageable pageable);
}
