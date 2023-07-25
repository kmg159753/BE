package com.example.newnique.user.controller;

import com.example.newnique.user.dto.SignupRequestDto;
import com.example.newnique.user.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
@RestController
@Slf4j(topic = "UserController")
@RequestMapping("/auth")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signup(@Valid @RequestBody SignupRequestDto signupRequestDto, BindingResult result){
        Map<String, String> response = new HashMap<>();
        if(result.hasErrors()) {
            String errorMsg = result.getFieldError().getDefaultMessage();
            response.put("result", "fail");
            response.put("msg", errorMsg);

            return ResponseEntity.badRequest().body(response);
        }

        String msg = userService.signup(signupRequestDto);

        response.put("msg", msg);

        return ResponseEntity.ok(response);
    }

}
