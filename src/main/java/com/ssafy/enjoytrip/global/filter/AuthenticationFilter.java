package com.ssafy.enjoytrip.global.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.enjoytrip.global.auth.PrincipalDetails;
import com.ssafy.enjoytrip.global.auth.dto.LoginRequestDto;
import com.ssafy.enjoytrip.global.util.AuthenticationUtil;
import com.ssafy.enjoytrip.global.util.SessionUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.io.IOException;

//스프링 시큐리티에 있는 UsernamePasswordAuthenticationFilter 필터를 냠냠한다
@RequiredArgsConstructor
@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    // Authentication 객체 만들어서 리턴 => 의존 : AuthenticationManager
    // 인증 요청시에 실행되는 함수 => /login
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        // request에 있는 username과 password를 파싱해서 자바 Object로 받기
        ObjectMapper om = new ObjectMapper();
        LoginRequestDto loginRequestDto = null;
        try {
            loginRequestDto = om.readValue(request.getInputStream(), LoginRequestDto.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 유저네임패스워드 토큰 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getId(),
                        loginRequestDto.getPassword());

        // authenticate() 함수가 호출 되면 인증 프로바이더가 유저 디테일 서비스의
        // loadUserByUsername(토큰의 첫번째 파라메터) 를 호출하고
        // UserDetails를 리턴받아서 토큰의 두번째 파라메터(credential)과
        // UserDetails(DB값)의 getPassword()함수로 비교해서 동일하면
        // Authentication 객체를 만들어서 필터체인으로 리턴해준다.
        log.info("id : {}, password{}",loginRequestDto.getId(), loginRequestDto.getPassword());
        Authentication authentication =
                authenticationManager.authenticate(authenticationToken);

        return authentication;
    }

    // 성공시 무시기 저시기
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();
        log.info("principal : {}", principalDetails.getUsername());



//        세션 아이디를 SESSION 이라는 이름의 쿠키에 저장
//        response.addCookie(new Cookie("SESSION", session.getId()));
//        Authentication authentication = new UsernamePasswordAuthenticationToken(
//                principalDetails, // 나중에 컨트롤러에서 DI해서 쓸 때 사용하기 편함.
//                null,     // 패스워드는 모르니까 null 처리, 어차피 지금 인증하는게 아니니까!!
//                principalDetails.getAuthorities());

        HttpSession session = request.getSession(true);
        session.setAttribute("membername",session.getId());
        SessionUtil.setSessionId(session.getId(), session);

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authResult);
        SecurityContextHolder.setContext(context);

        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
//
//        log.info("????? {} ",authentication.getPrincipal());
//        log.info("????? {} ",authentication.getName());
//        log.info("????? {} ",authentication.getAuthorities());
//        log.info("????? {} ",authentication.getDetails());
//
        String userName = AuthenticationUtil.authenticationGetUsername();
        log.info("username : {}", userName);
        Authentication authentication2 = SecurityContextHolder.getContext().getAuthentication();
        log.info("레전드 상황 {} ",authentication2.getPrincipal());
        log.info("레전드 상황 {} ",authentication2.isAuthenticated());
        log.info("레전드 상황 {} ",authentication2.getName());
        log.info("레전드 상황 {} ",authentication2.getAuthorities());
        log.info("레전드 상황 {} ",authentication2.getDetails());

        response.addHeader("memberSeq", principalDetails.getSeq()+"");
        response.addHeader("userProfileImage", principalDetails.getUserProfileImage());
    }
}
