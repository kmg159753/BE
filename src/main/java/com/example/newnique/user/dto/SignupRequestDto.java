package com.example.newnique.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class SignupRequestDto {

    @Size(min = 3, max = 10, message = "이름은 3자이상 10자 이하로 입력해주세요.")
    @Pattern(regexp = "^[가-힣a-zA-Z0-9]{3,10}$", message = "유효하지 않은 닉네임 형식입니다.")
    @NotBlank(message = "이름을 입력해주세요")
    private final String nickname;

    @Size(min = 6, max = 15, message = "비밀번호는 6자이상 15자 이하로 입력해주세요.")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z]).{6,15}$", message = "비밀번호는 알파벳(소문자), 숫자를 포함해야 합니다.")
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private final String userPassword;


    @Email(message = "유효하지 않은 이메일 형식입니다.")
    @NotBlank(message = "이메일을 입력 해주세요")
    private final String userEmail;

    private final String emoji;
}