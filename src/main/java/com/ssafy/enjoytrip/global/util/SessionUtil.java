package com.ssafy.enjoytrip.global.util;

import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashMap;
import java.util.Map;

public class SessionUtil {
    private SessionUtil() {
        throw new IllegalStateException("Utility class");
    }
    public static Map<String, HttpSession> map = new HashMap<>();
    @Getter
    public static String sessionId;
    public static void setSessionId(String id, HttpSession session){
        sessionId = id;
        map.put(id, session);
    }
}
