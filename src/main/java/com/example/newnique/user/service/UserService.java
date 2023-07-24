package com.example.newnique.user.service;


import com.example.newnique.auth.jwt.JwtUtil;
import com.example.newnique.user.dto.LoginRequestDto;
import com.example.newnique.user.dto.SignupRequestDto;
import com.example.newnique.user.entity.User;
import com.example.newnique.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.example.newnique.auth.jwt.JwtUtil.AUTHORIZATION_HEADER;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public String signup( SignupRequestDto requestDto) {

        Optional<User> checkUserId =  userRepository.findByNickname(requestDto.getNickname());
        if(checkUserId.isPresent()){
            throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
        }

        Optional<User> checkEmail = userRepository.findByUserEmail(requestDto.getUserEmail());
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        User user = new User(requestDto.getUserEmail(),passwordEncoder.encode(requestDto.getUserPassword()),requestDto.getNickname());
        userRepository.save(user);

        return "회원가입에 성공했습니다";
    }

    public String login(LoginRequestDto requestDto, HttpServletResponse response) {
        User user = userRepository.findByUserEmail(requestDto.getMemberEmail())
                .orElseThrow(() -> new IllegalArgumentException("이메일이 존재하지 않습니다."));

        if (!passwordEncoder.matches(requestDto.getMemberPw(), user.getUserPassword())) {
            throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
        }

        String token = jwtUtil.createToken(user.getUserEmail());
        response.setHeader(AUTHORIZATION_HEADER, token);
        return token;
    }

}