package com.guarajunior.guararp.infra.repository;

import com.guarajunior.guararp.infra.model.RegisterToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegisterTokenRepository extends JpaRepository<RegisterToken, Integer> {
	RegisterToken findByToken(String token);
}
