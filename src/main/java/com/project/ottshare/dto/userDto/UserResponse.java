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

    public UserResponse(User user) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.phoneNumber = user.getPhoneNumber();
        this.bank = user.getBank();
        this.account = user.getAccount();
        this.accountHolder = user.getAccountHolder();
        this.role = user.getRole();
        this.isShareRoom = user.isShareRoom();
    }

    public User toEntity() {
        return User.builder()
                .userId(userId)
                .username(username)
                .password(password)
                .email(email)
                .nickname(nickname)
                .phoneNumber(phoneNumber)
                .bank(bank)
                .account(account)
                .accountHolder(accountHolder)
                .role(role)
                .isShareRoom(isShareRoom)
                .build();
    }
}
