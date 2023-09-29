package com.guarajunior.rp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.guarajunior.rp.model.Department;
import com.guarajunior.rp.model.ModelUserDetails;
import com.guarajunior.rp.model.Role;
import com.guarajunior.rp.model.RoleDepartmentPrivilege;
import com.guarajunior.rp.model.User;
import com.guarajunior.rp.repository.RoleDepartmentPrivilegeRepository;
import com.guarajunior.rp.repository.UserRepository;

@Service
public class ServiceUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleDepartmentPrivilegeRepository roleDepartmentPrivilegeRepository;

    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(login);
        if (user == null) {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }

        ModelUserDetails userDetails = buildUserDetails(user);

        return userDetails;
    }

    private ModelUserDetails buildUserDetails(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        
        Role role = user.getRole();
        Department department = user.getDepartment();
        if (role != null && department != null) {
	        List<RoleDepartmentPrivilege> roleDepartmentPrivileges = roleDepartmentPrivilegeRepository
	        		.findRoleDepartmentPrivileges(role, department);
	        
	        for (RoleDepartmentPrivilege privilege : roleDepartmentPrivileges) {
	            authorities.add(new SimpleGrantedAuthority(privilege.getUserPrivilege().getName()));
	        }
        }

        return new ModelUserDetails(
            user.getId(),
            user.getLogin(),
            user.getPassword(),
            user.getActive(),
            user.getStatus(),
            authorities
        );
    }
}