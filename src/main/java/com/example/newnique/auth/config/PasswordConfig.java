package com.example.newnique.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordConfig { //Bean으로 저장될 때 P가 아닌 소문자p로 passwordConfig라고 저장됨.

    @Bean
    public PasswordEncoder passwordEncoder() { //빈으로 등록하고자 하는 객체를 반환하는 메서드 선언
        return new BCryptPasswordEncoder(); //BCrypt는 비밀번호를 암호화 해주는 Hash함수이다.
    }
}