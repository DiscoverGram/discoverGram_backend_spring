package com.ssafy.enjoytrip.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
public class AuthenticationUtil {
    private AuthenticationUtil() {
        throw new IllegalStateException("Utility class");
    }
    public static String authenticationGetUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
