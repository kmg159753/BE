package com.example.newnique.user.controller;


import com.example.newnique.auth.jwt.JwtUtil;
import com.example.newnique.user.dto.LoginRequestDto;
import com.example.newnique.user.dto.SignupRequestDto;
import com.example.newnique.user.service.KakaoService;
import com.example.newnique.user.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j(topic = "UserController")
@RequestMapping("/api/auth")
public class UserController {
    private final UserService userService;
    private final KakaoService kakaoService;

    public UserController(UserService userService,KakaoService kakaoService) {
        this.userService = userService;
        this.kakaoService = kakaoService;

    }

    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signup(@Valid @RequestBody SignupRequestDto signupRequestDto, BindingResult result) {
        Map<String, String> response = new HashMap<>();
        if (result.hasErrors()) {
            String errorMsg = result.getFieldError().getDefaultMessage();
            response.put("result", "fail");
            response.put("msg", errorMsg);

            return ResponseEntity.badRequest().body(response);
        }

        String msg = userService.signup(signupRequestDto);

        response.put("msg", msg);

        return ResponseEntity.ok(response);
    }


    

    @GetMapping("kakao/callback")
    public String kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        String token = kakaoService.kakaoLogin(code);

        System.out.println(token);

        response.setHeader("AUTHORIZATION_HEADER",token);
        System.out.println(response);

        return "redirect:/";
    }

}
