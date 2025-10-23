package com.coreone.back.infrastructure.security;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public interface JwtService {
    String generateToken(CustomUserDetails user);
    String extractEmail(String token);
    Collection<? extends GrantedAuthority> extractAuthorities(String token);
    boolean isTokenValid(String token, UserDetails userDetails);
}
