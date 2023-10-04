package com.guarajunior.rp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guarajunior.rp.model.PasswordResetToken;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Integer> {
	PasswordResetToken findByToken(String token);
}
