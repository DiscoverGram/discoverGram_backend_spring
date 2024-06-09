package com.ssafy.enjoytrip.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); //서버가 응답할 때 json을 자바스크립트에서 처리할 수 있게 할지를 설정하는 것
        config.addAllowedOrigin("http://localhost:5173");  // 모듬 ip 응답 허용
        config.addAllowedHeader("*"); // 모든 header에 응답 허용
        config.addAllowedMethod("GET"); // 모든 메서드에 응답 허용
        config.addAllowedMethod("POST"); // 모든 메서드에 응답 허용
        config.addAllowedMethod("PUT"); // 모든 메서드에 응답 허용
        config.addAllowedMethod("DELETE"); // 모든 메서드에 응답 허용
        config.addAllowedMethod("OPTIONS"); // 모든 메서드에 응답 허용
        config.addExposedHeader("memberSeq");
        config.addExposedHeader("userProfileImage");
        source.registerCorsConfiguration("/**",config);
        return new CorsFilter(source);

    }
}
