package com.example.newnique.auth.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // Spring Security 지원을 가능하게 함
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // CSRF 설정
        // CSRF 를 사용하지 않을 것이기 때문에 disable로 설정
        http.csrf((csrf) -> csrf.disable());
        // 현재 생성한 Filter 의 기능과 동일
        http.authorizeHttpRequests((authorizeHttpRequests) ->
                authorizeHttpRequests
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll() // resources(정적리소스) 접근 허용
                        .requestMatchers("/auth/**").permitAll()//은 "/auth"로 시작하는 모든 url을 접근 허용
                        .anyRequest().authenticated() // 그 외 모든 요청은 인증처리
        );

        return http.build();
    }
}