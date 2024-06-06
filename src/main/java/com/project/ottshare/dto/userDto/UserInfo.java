package com.project.ottshare.dto.userDto;

import com.project.ottshare.entity.User;
import com.project.ottshare.enums.BankType;
import com.project.ottshare.enums.Role;
import com.project.ottshare.security.auth.CustomUserDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {

    private Long userId;
    private String username;
    private String password;
    private String nickname;
    private String email;
    private String name;
    private String phoneNumber;
    private BankType bank;
    private String account;
    private String accountHolder;
    private Role role;
    private boolean isShareRoom;

    public UserInfo(CustomUserDetails userDetails) {
        this.userId = userDetails.getId();
        this.username = userDetails.getUsername();
        this.password = userDetails.getPassword();
        this.nickname = userDetails.getNickname();
        this.email = userDetails.getEmail();
        this.name = userDetails.getName();
        this.phoneNumber = userDetails.getPhoneNumber();
        this.bank = userDetails.getBank();
        this.account = userDetails.getAccount();
        this.accountHolder = userDetails.getAccountHolder();
        this.role = userDetails.getRole();
        this.isShareRoom = userDetails.isShareRoom();
    }

    public UserInfo(User user) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.name = user.getName();
        this.phoneNumber = user.getPhoneNumber();
        this.bank = user.getBank();
        this.account = user.getAccount();
        this.accountHolder = user.getAccountHolder();
        this.role = user.getRole();
        this.isShareRoom = user.isShareRoom();
    }

    public UserRequest toUserRequest() {
        return UserRequest.builder()
                .username(this.username)
                .password(this.password)
                .nickname(this.nickname)
                .email(this.email)
                .name(this.name)
                .phoneNumber(this.phoneNumber)
                .bank(this.bank)
                .account(this.account)
                .accountHolder(this.accountHolder)
                .role(this.role)
                .build();
    }

}
