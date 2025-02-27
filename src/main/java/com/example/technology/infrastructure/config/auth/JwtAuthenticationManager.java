package com.example.technology.infrastructure.config.auth;

import io.jsonwebtoken.*;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {
    private final String secretKey;

    public JwtAuthenticationManager() {
        this.secretKey = JwtSecretReader.readSecretKey();
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String token = authentication.getCredentials().toString();
        return Mono.fromCallable(() -> {
                    Claims claims = Jwts.parserBuilder()
                            .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
                            .build()
                            .parseClaimsJws(token)
                            .getBody();

                    String username = claims.getSubject();
                    UserDetails userDetails = User.withUsername(username)
                            .password("")
                            .authorities(List.of())
                            .build();

                    return new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());
                }).map(Authentication.class::cast)
                .onErrorResume(e -> Mono.empty());

    }
}
