package com.guarajunior.guararp.api.dto.course.response;

import com.guarajunior.guararp.api.dto.user.response.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseWithUsersResponse {
    private String name;
    private List<UserResponse> users;
}
