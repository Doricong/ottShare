package com.project.ottshare.dto.userDto;

import com.project.ottshare.entity.User;
import com.project.ottshare.enums.BankType;
import com.project.ottshare.enums.Role;
import com.project.ottshare.validation.ValidationGroups.NotBlankGroups;
import com.project.ottshare.validation.ValidationGroups.PatternGroups;
import com.project.ottshare.validation.ValidationGroups.RangeGroups;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Range;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserRequest {

    @NotBlank(message = "이름은 필수 입력 값입니다", groups = NotBlankGroups.class)
    private String name;

    @NotBlank(message = "아이디는 필수 입력 값입니다.", groups = NotBlankGroups.class)
    @Pattern(regexp = "^[a-zA-Z0-9]{4,20}$", message = "아이디는 특수문자를 제외한 4~20자 사이로 입력해주세요.", groups = PatternGroups.class)
    private String username;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.", groups = NotBlankGroups.class)
    @Range(min = 8, max = 16, message = "비밀번호는 8~16자 사이로 입력해주세요.", groups = RangeGroups.class)
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=!])(?!.*\\s).{8,16}$", message = "비밀번호는 영문, 숫자, 특수문자 모두 포함해주세요.", groups = PatternGroups.class)
    private String password;

    @NotBlank(message = "닉네임은 필수 입력 값입니다.", groups = NotBlankGroups.class)
    @Pattern(regexp = "^[가-힣a-zA-Z0-9]{2,10}$", message = "닉네임은 2~10자의 한글, 영문, 숫자만 입력해주세요.", groups = PatternGroups.class)
    private String nickname;

    @NotBlank(message = "이메일은 필수 입력 값입니다.", groups = NotBlankGroups.class)
    @Pattern(regexp = "\\w+@\\w+\\.\\w+(\\.\\w+)?", message = "이메일 형식이 올바르지 않습니다.", groups = PatternGroups.class)
    private String email;

    @NotBlank(message = "휴대폰 번호는 필수 입력 값입니다.", groups = NotBlankGroups.class)
    @Pattern(regexp = "^01(?:0|1|[6-9])[0-9]{7,8}$", message = "올바른 휴대폰 번호 형식이 아닙니다.", groups = PatternGroups.class)
    private String phoneNumber;

    @NotBlank(message = "계좌번호는 필수 입력 값입니다.", groups = NotBlankGroups.class)
    private String account;

    @NotBlank(message = "예금주는 필수 입력 값입니다.", groups = NotBlankGroups.class)
    private String accountHolder;

    private BankType bank;

    private Role role;

    public User toEntity() {
        User user = User.builder()
                .name(name)
                .username(username)
                .password(password)
                .nickname(nickname)
                .email(email)
                .phoneNumber(phoneNumber)
                .account(account)
                .accountHolder(accountHolder)
                .bank(bank)
                .role(role)
                .build();

        return user;
    }

    public UserRequest(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }
}
