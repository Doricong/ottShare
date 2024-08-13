package com.project.ottshare.dto.userDto;

import com.project.ottshare.entity.OttShareRoom;
import com.project.ottshare.entity.User;
import com.project.ottshare.enums.BankType;
import com.project.ottshare.enums.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    private Long userId;

    private String name;

    private String username;

    private String password;

    private String email;

    private String nickname;

    private String phoneNumber;

    private BankType bank;

    private String account;

    private String accountHolder;

    private Role role;

    private boolean isShareRoom;

    public static UserResponse from(User user) {
        return UserResponse.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .phoneNumber(user.getPhoneNumber())
                .bank(user.getBank())
                .account(user.getAccount())
                .accountHolder(user.getAccountHolder())
                .role(user.getRole())
                .isShareRoom(user.isShareRoom())
                .build();
    }
}
