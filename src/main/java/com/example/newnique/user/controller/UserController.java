package com.example.newnique.user.controller;

import com.example.newnique.user.dto.LoginRequestDto;
import com.example.newnique.user.dto.SignupRequestDto;
import com.example.newnique.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
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
        log.info("msg : {}", msg);

        response.put("result", "success");
        response.put("msg", msg);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        Map<String, String> res = new HashMap<>();

        try {
            String token = userService.login(loginRequestDto, response);

            res.put("result", "success");
            res.put("msg", "로그인에 성공했습니다.");
            res.put("token", token);

            return ResponseEntity.ok(res);
        } catch (IllegalArgumentException e) {
            res.put("result", "fail");
            res.put("msg", e.getMessage());

            return ResponseEntity.badRequest().body(res);
        } catch (Exception e) {
            res.put("result", "fail");
            res.put("msg", "로그인 과정에서 일시적인 오류가 발생했습니다.");

            return ResponseEntity.badRequest().body(res);
        }
    }
}
