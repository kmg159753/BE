package com.example.newnique.user.service;

import com.example.newnique.user.dto.SignupRequestDto;
import com.example.newnique.user.entity.User;
import com.example.newnique.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static com.example.newnique.global.exception.ErrorCode.NOT_FOUND_DATA;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public String signup(SignupRequestDto requestDto) {

        Optional<User> checkEmail = userRepository.findByUserEmail(requestDto.getUserEmail());
        if (checkEmail.isPresent()) {
            throw new ResponseStatusException(NOT_FOUND_DATA.getStatus(), NOT_FOUND_DATA.formatMessage("이메일"));
        }

        userRepository.save(
                User.builder()
                        .userEmail(requestDto.getUserEmail())
                        .userPassword(passwordEncoder.encode(requestDto.getUserPassword()))
                        .nickname(requestDto.getNickname())
                        .emoji(requestDto.getEmoji())
                        .build()
        );

        return "회원가입이 완료되었습니다.";
    }
}