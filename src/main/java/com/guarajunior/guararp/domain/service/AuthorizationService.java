package com.guarajunior.guararp.domain.service;

import com.guarajunior.guararp.infra.model.CustomUserDetails;
import com.guarajunior.guararp.infra.model.Department;
import com.guarajunior.guararp.infra.model.Permission;
import com.guarajunior.guararp.infra.model.User;
import com.guarajunior.guararp.infra.repository.UserRepository;
import com.guarajunior.guararp.util.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorizationService implements UserDetailsService {
    private final UserRepository userRepository;

    public AuthorizationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        List<GrantedAuthority> authorities = new ArrayList<>();
        List<Permission> permissions = user.getRole().getPermissions();

        if (permissions != null){
            Department department = user.getDepartment();

            for (Permission permission : permissions) {
                authorities.add(new SimpleGrantedAuthority(buildAuthorityString(permission.name(), department.getName())));
            }
        }

        return new CustomUserDetails(user.getId(), user.getLogin(), user.getPassword(), user.getActive(), user.getStatus(), authorities);
    }

    private String buildAuthorityString(String privilege, String department) {
        String departmentUpperCase = StringUtils.toScreamingSnakeCase(department);
        return privilege + "_ON_" + departmentUpperCase;
    }
}
