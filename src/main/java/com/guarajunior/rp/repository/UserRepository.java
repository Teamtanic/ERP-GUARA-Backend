package com.guarajunior.rp.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import com.guarajunior.rp.model.User;

public interface UserRepository extends JpaRepository<User, UUID> {
	@Query("SELECT u FROM User u WHERE u.active = true")
	Page<User> findAll(Pageable pageable);
	
	UserDetails findByLogin(String login);
}
