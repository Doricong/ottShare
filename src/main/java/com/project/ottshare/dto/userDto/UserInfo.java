package com.project.ottshare.dto.userDto;

import com.project.ottshare.enums.BankType;
import com.project.ottshare.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}
