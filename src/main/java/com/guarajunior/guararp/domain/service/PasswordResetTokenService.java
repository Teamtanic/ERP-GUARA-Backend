package com.guarajunior.guararp.domain.service;

import com.guarajunior.guararp.api.error.exception.InvalidTokenException;
import com.guarajunior.guararp.infra.model.PasswordResetToken;
import com.guarajunior.guararp.infra.model.RegisterToken;
import com.guarajunior.guararp.infra.repository.PasswordResetTokenRepository;
import com.guarajunior.guararp.infra.repository.RegisterTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PasswordResetTokenService {
    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;
    @Autowired
    private RegisterTokenRepository registerTokenRepository;

    public PasswordResetToken validatePasswordResetToken(String token) {
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token);

        if (resetToken == null || resetToken.isExpired()) {
            throw new InvalidTokenException("Token inválido ou expirado");
        }

        return resetToken;
    }

    public RegisterToken validateRegisterTokenToken(String token) {
        RegisterToken resetToken = registerTokenRepository.findByToken(token);

        if (resetToken == null || resetToken.isExpired()) {
            throw new InvalidTokenException("Token inválido ou expirado");
        }

        return resetToken;
    }
}
