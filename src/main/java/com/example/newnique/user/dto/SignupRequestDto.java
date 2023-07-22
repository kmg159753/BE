package com.example.newnique.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SignupRequestDto {

    @Size(min = 3, max = 10, message = "이름은 3자이상 10자 이하로 입력해주세요.")
    @Pattern(regexp = "^[가-힣a-zA-Z0-9]{3,10}$", message = "유효하지 않은 닉네임 형식입니다.")
    @NotBlank(message = "이름을 입력해주세요")
    private String nickname;

    @Size(min = 6, max = 15, message = "비밀번호는 6자이상 15자 이하로 입력해주세요.")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z]).{6,15}$", message = "비밀번호는 알파벳(소문자), 숫자를 포함해야 합니다.")
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String userPassword;


    @Pattern(regexp = "^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[A-Za-z]+$", message = "유효하지 않은 이메일 형식입니다.")
    @NotBlank(message = "이메일을 입력 해주세요")
    private String userEmail;
}