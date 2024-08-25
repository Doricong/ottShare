package com.project.ottshare.dto.userDto;

import com.project.ottshare.validation.ValidationGroups;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class VerifyCodeRequest {

    @NotBlank(message = "이름은 필수 입력 값입니다", groups = ValidationGroups.NotBlankGroups.class)
    private String name;

    @NotBlank(message = "휴대폰 번호는 필수 입력 값입니다.", groups = ValidationGroups.NotBlankGroups.class)
    @Pattern(regexp = "^01(?:0|1|[6-9])[0-9]{7,8}$", message = "올바른 휴대폰 번호 형식이 아닙니다.", groups = ValidationGroups.PatternGroups.class)
    private String phoneNumber;
}
