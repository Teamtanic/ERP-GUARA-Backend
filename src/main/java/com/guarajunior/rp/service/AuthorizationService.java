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

import com.guarajunior.rp.model.CustomUserDetails;
import com.guarajunior.rp.model.Department;
import com.guarajunior.rp.model.Role;
import com.guarajunior.rp.model.User;
import com.guarajunior.rp.model.UserPrivilege;
import com.guarajunior.rp.repository.RoleDepartmentPrivilegeRepository;
import com.guarajunior.rp.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class AuthorizationService implements UserDetailsService {
	@Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleDepartmentPrivilegeRepository roleDepartmentPrivilegeRepository;

    @Override
	@Transactional
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(login);
        if (user == null) {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }
        
        List<GrantedAuthority> authorities = new ArrayList<>();
        
        Role role = user.getRole();
        Department department = user.getDepartment();
        
        if (role != null && department != null) {
	        List<UserPrivilege> privileges = roleDepartmentPrivilegeRepository
	        		.findUserPrivilegesByRoleAndDepartment(role, department);
	        
	        authorities.add(new SimpleGrantedAuthority(department.getName()));
	        
	        for (UserPrivilege privilege : privileges) {
	            authorities.add(new SimpleGrantedAuthority(privilege.getName()));
	        }
        }

        UserDetails userDetails = new CustomUserDetails(
        		user.getId(),
                user.getLogin(),
                user.getPassword(),
                user.getActive(),
                user.getStatus(),
                authorities
            );

        return userDetails;
    }

}
