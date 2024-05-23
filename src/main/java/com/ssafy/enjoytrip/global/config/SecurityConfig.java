package com.ssafy.enjoytrip.global.config;



import com.ssafy.enjoytrip.domain.member.repository.MemberRepository;
import com.ssafy.enjoytrip.global.auth.PrincipalDetailsService;
import com.ssafy.enjoytrip.global.filter.AuthenticationFilter;
//import com.ssafy.enjoytrip.global.filter.AuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;

import static org.springframework.security.core.context.SecurityContextHolder.MODE_INHERITABLETHREADLOCAL;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    @Autowired
    private CorsConfig corsConfig;
    @Autowired
    private PrincipalDetailsService principalDetailsService;

    @Bean
    public DelegatingSecurityContextRepository delegatingSecurityContextRepository() {
        return new DelegatingSecurityContextRepository(
                new RequestAttributeSecurityContextRepository(),
                new HttpSessionSecurityContextRepository()
        );
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, MemberRepository memberRepository) throws Exception {

        SecurityContextHolder.setStrategyName(MODE_INHERITABLETHREADLOCAL);
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(principalDetailsService).passwordEncoder(passwordEncoder());

        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        return http
            .authorizeHttpRequests((auth) -> auth
                    .requestMatchers("/signup").permitAll()
//                    .anyRequest().authenticated()
                            .anyRequest().permitAll()
            )
            .securityContext((securityContext) -> securityContext
                    .requireExplicitSave(true)
            )
            .logout(httpSecurityLogoutConfigurer -> {
                httpSecurityLogoutConfigurer.logoutSuccessUrl("/");
//                httpSecurityLogoutConfigurer.deleteCookies("principal");
                httpSecurityLogoutConfigurer.permitAll();
            })
            .authenticationManager(authenticationManager)
            .addFilterBefore(new SecurityContextPersistenceFilter(), SecurityContextPersistenceFilter.class)
            .addFilter(corsConfig.corsFilter())
                .addFilter(new AuthenticationFilter(authenticationManager, delegatingSecurityContextRepository()))
//                .addFilter(new AuthorizationFilter(authenticationManager, memberRepository))
            .formLogin(AbstractHttpConfigurer::disable)
            .csrf(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
            .sessionManagement((sessionManagement) -> sessionManagement.sessionFixation().none()
                    .maximumSessions(1)
                    .maxSessionsPreventsLogin(true))
            .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
