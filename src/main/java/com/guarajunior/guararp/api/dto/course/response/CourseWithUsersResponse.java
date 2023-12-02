package com.guarajunior.guararp.api.dto.course.response;

import com.guarajunior.guararp.api.dto.user.response.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseWithUsersResponse {
    private UUID id;
    private String name;
    private List<UserResponse> users;
}
