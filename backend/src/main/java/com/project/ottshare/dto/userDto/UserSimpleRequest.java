package com.project.ottshare.dto.userDto;

import com.project.ottshare.enums.BankType;
import com.project.ottshare.validation.ValidationGroups;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Range;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSimpleRequest {

    private Long id;

    @NotBlank(message = "아이디는 필수 입력 값입니다.", groups = ValidationGroups.NotBlankGroups.class)
    @Pattern(regexp = "^[a-zA-Z0-9]{4,20}$", message = "아이디는 특수문자를 제외한 4~20자 사이로 입력해주세요.", groups = ValidationGroups.PatternGroups.class)
    private String username;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.", groups = ValidationGroups.NotBlankGroups.class)
//    @Range(min = 8, max = 16, message = "8~16자 사이로 입력해주세요.", groups = ValidationGroups.RangeGroups.class)
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=!])(?!.*\\s).{8,16}$", message = "비밀번호는 영문, 숫자, 특수문자 모두 포함해주세요.", groups = ValidationGroups.PatternGroups.class)
    private String password;

    @NotBlank(message = "닉네임은 필수 입력 값입니다.", groups = ValidationGroups.NotBlankGroups.class)
    @Pattern(regexp = "^[가-힣a-zA-Z0-9]{2,10}$", message = "2~10자의 한글, 영문, 숫자만 입력해주세요.", groups = ValidationGroups.PatternGroups.class)
    private String nickname;

    @NotBlank(message = "계좌번호는 필수 입력 값입니다.", groups = ValidationGroups.NotBlankGroups.class)
    private String account;

    @NotBlank(message = "예금주는 필수 입력 값입니다.", groups = ValidationGroups.NotBlankGroups.class)
    private String accountHolder;

    private BankType bank;
}
