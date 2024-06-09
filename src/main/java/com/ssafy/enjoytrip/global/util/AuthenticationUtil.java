package com.ssafy.enjoytrip.global.util;

import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

public class AuthenticationUtil {
    private AuthenticationUtil() {
        throw new IllegalStateException("Utility class");
    }
    public static String authenticationGetUsername() {
        HttpSession session = SessionUtil.map.get(SessionUtil.getSessionId());
        SecurityContext context = (SecurityContext)session.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
        Authentication authentication = context.getAuthentication();
        return authentication.getName();
    }
}