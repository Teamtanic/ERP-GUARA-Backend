package com.guarajunior.guararp.domain.mapper;

import com.guarajunior.guararp.api.dto.course.response.CourseWithUsersResponse;
import com.guarajunior.guararp.infra.model.Course;
import org.springframework.stereotype.Component;

@Component
public class CourseWithUsersMapper {
    private final UserMapper userMapper;

    public CourseWithUsersMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public CourseWithUsersResponse toResponseDTO(Course course) {
        return new CourseWithUsersResponse(course.getName(), course.getUsers().stream().map(userMapper::toResponseDTO).toList());
    }
}
