package com.project.ottshare.dto.userDto;

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


}
