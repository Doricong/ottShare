package com.project.ottshare.dto.userDto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.project.ottshare.validation.ValidationGroups.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserRequest {

    @NotBlank(groups = NotBlankGroups.class, message = "아이디 입력은 필수입니다.")
    private String username;

    @NotBlank(groups = NotBlankGroups.class, message = "비밀번호 입력은 필수입니다.")
    private String password;

}
