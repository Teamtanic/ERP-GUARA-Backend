package com.guarajunior.guararp.domain.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.guarajunior.guararp.infra.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;
    @Autowired
    private AuthorizationService authorizationService;

    public String generateToken(User user) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        UserDetails userDetails = authorizationService.loadUserByUsername(user.getLogin());
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        List<String> authorityStrings = authorities.stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());
        return JWT.create().withIssuer("auth-api").withSubject(user.getLogin()).withExpiresAt(genExpirationDate()).withClaim("permissions", authorityStrings).sign(algorithm);
    }

    public String validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.require(algorithm).withIssuer("auth-api").build().verify(token).getSubject();
    }

    private Instant genExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
