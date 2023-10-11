package com.guarajunior.rp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guarajunior.rp.model.RegisterToken;

public interface RegisterTokenRepository extends JpaRepository<RegisterToken, Integer> {
	RegisterToken findByToken(String token);
}
