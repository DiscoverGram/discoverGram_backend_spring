package com.ssafy.enjoytrip.global.config;



import com.ssafy.enjoytrip.domain.member.repository.MemberRepository;
import com.ssafy.enjoytrip.global.auth.PrincipalDetailsService;
import com.ssafy.enjoytrip.global.filter.AuthenticationFilter;
import com.ssafy.enjoytrip.global.filter.AuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    @Autowired
    private CorsConfig corsConfig;
    @Autowired
    private PrincipalDetailsService principalDetailsService;
    @Autowired
    private MemberRepository memberRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, MemberRepository memberRepository) throws Exception {

        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(principalDetailsService).passwordEncoder(passwordEncoder());

        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        return http
            .authorizeHttpRequests((auth) -> auth
                    .requestMatchers("/signup").permitAll()
                    .anyRequest().authenticated()
//                            .anyRequest().permitAll()
            )
            .logout(httpSecurityLogoutConfigurer -> {
                httpSecurityLogoutConfigurer.logoutSuccessUrl("/");
//                httpSecurityLogoutConfigurer.deleteCookies("principal");
                httpSecurityLogoutConfigurer.permitAll();
            })
            .authenticationManager(authenticationManager)
            .addFilter(corsConfig.corsFilter())
            .addFilter(new AuthenticationFilter(authenticationManager))
                .addFilter(new AuthorizationFilter(authenticationManager, memberRepository))
            .formLogin(AbstractHttpConfigurer::disable)
            .csrf(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
//            .sessionManagement((sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .sessionManagement((sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
            .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
