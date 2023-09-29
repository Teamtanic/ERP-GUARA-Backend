package com.guarajunior.rp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.guarajunior.rp.repository.UserRepository;

@Service
public class AuthorizationService implements UserDetailsService {
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return customUserDetailsService.loadUserByUsername(username);
	}

}
