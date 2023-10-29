package com.guarajunior.guararp.repository;

import com.guarajunior.guararp.model.RegisterToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegisterTokenRepository extends JpaRepository<RegisterToken, Integer> {
	RegisterToken findByToken(String token);
}
