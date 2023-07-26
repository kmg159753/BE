package com.example.newnique.auth.filter;


import com.example.newnique.auth.jwt.JwtUtil;
import com.example.newnique.auth.security.UserDetailsImpl;
import com.example.newnique.user.dto.LoginRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.io.PrintWriter;

import static com.example.newnique.auth.jwt.JwtUtil.AUTHORIZATION_HEADER;


@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/api/auth/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("로그인 시도");
        try {
            LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);

            return getAuthenticationManager().authenticate( // authenticate - 인증 처리를 하는 메소드
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getUserEmail(),
                            requestDto.getUserPassword()
                    )
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override // 성공메시지 반환하는 법 찾아서 같이 반환해주기
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String username = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();

        String token = jwtUtil.createToken(username);
        response.addHeader(AUTHORIZATION_HEADER, token);
        // 응답 상태, 타입, 인코딩 설정
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        // JSON 응답 생성
        String json = "{\"msg\": \"로그인이 완료 되었습니다.\"}";

        // JSON 응답 전송
        PrintWriter writer = response.getWriter();
        writer.print(json);
        writer.flush();
    }

    @Override // 오류메시지 반환하는 법 찾아서 같이 반환해주기 printer wr
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        // 응답 상태, 타입, 인코딩 설정
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        // JSON 응답 생성
        String json = "{\"message\": \"이메일 혹은 비밀번호가 일치하지 않습니다.\",\"statusCode\": \"401\"}";


        // JSON 응답 전송
        PrintWriter writer = response.getWriter();
        writer.print(json);
        writer.flush();
        response.setStatus(401);
    }
}

