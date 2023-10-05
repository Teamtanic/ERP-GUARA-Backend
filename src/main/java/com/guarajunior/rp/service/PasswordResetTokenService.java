package com.guarajunior.rp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guarajunior.rp.exception.InvalidTokenException;
import com.guarajunior.rp.model.PasswordResetToken;
import com.guarajunior.rp.repository.PasswordResetTokenRepository;

@Service
public class PasswordResetTokenService {
	 @Autowired
	    private PasswordResetTokenRepository passwordResetTokenRepository;

	    public PasswordResetToken validateToken(String token) {
	        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token);

	        if (resetToken == null || resetToken.isExpired()) {
	            throw new InvalidTokenException("Token inválido ou expirado");
	        }

	        return resetToken;
	    }
}
