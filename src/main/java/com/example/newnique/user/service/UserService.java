package com.example.newnique.user.service;

import com.example.newnique.exception.EmailAlreadyExistsException;
import com.example.newnique.user.dto.SignupRequestDto;
import com.example.newnique.user.entity.User;
import com.example.newnique.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public String signup( SignupRequestDto requestDto) {

        Optional<User> checkEmail = userRepository.findByUserEmail(requestDto.getUserEmail());
        if (checkEmail.isPresent()) {
            throw new EmailAlreadyExistsException("이미 존재하는 이메일입니다.");
        }

        User user = new User(requestDto.getUserEmail(),passwordEncoder.encode(requestDto.getUserPassword()),requestDto.getNickname(), requestDto.getEmoji());
        userRepository.save(user);

        return "회원가입이 완료되었습니다.";
    }
}