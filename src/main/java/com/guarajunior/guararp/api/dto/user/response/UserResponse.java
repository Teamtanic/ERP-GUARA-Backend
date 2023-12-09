package com.guarajunior.guararp.api.dto.user.response;

import com.guarajunior.guararp.api.dto.contact.response.ContactResponse;
import com.guarajunior.guararp.api.dto.course.response.CourseResponse;
import com.guarajunior.guararp.api.dto.department.response.DepartmentResponse;
import com.guarajunior.guararp.api.dto.role.response.RoleResponse;
import lombok.Data;

import java.util.UUID;

@Data
public class UserResponse {
    private UUID id;
    private String name;
    private String prontuary;
    private CourseResponse course;
    private DepartmentResponse department;
    private RoleResponse role;
    private ContactResponse contact;
    private Boolean status;
}
