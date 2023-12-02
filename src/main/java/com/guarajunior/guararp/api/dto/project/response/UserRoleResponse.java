package com.guarajunior.guararp.api.dto.project.response;

import com.guarajunior.guararp.api.dto.user.response.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleResponse {
    private UserResponse user;
    private String role;
}
