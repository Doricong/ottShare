package com.project.ottshare.entity;

import com.project.ottshare.dto.userDto.SocialUserRequest;
import com.project.ottshare.dto.userDto.UserRequest;
import com.project.ottshare.dto.userDto.UserResponse;
import com.project.ottshare.enums.BankType;
import com.project.ottshare.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User extends BaseTimeEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Inquiry> inquiries = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private SharingUser sharingUser;

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FAQ> faqs = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private WaitingUser waitingUser;

    @Column(name = "name", nullable = false)
    private String name;

    //중복x
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    //중복x
    @Column(name = "nickname", nullable = false, unique = true)
    private String nickname;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "bank", nullable = false)
    @Enumerated(EnumType.STRING)
    private BankType bank;

    @Column(name = "account", nullable = false)
    private String account;

    @Column(name = "account_holder", nullable = false)
    private String accountHolder;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "is_share_room", nullable = false, columnDefinition = "boolean default false")
    private boolean isShareRoom;




    /**
     * 비즈니스 로직
     */
    //user 정보(password, nickname) 수정
    public void update(String username, String password, String nickname, String account, String accountHolder, BankType bank) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.account = account;
        this.accountHolder = accountHolder;
        this.bank = bank;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void checkShareRoom() {
        this.isShareRoom = true;
    }

    public void leaveShareRoom() {
        this.isShareRoom = false;
    }

    public static User from(SocialUserRequest userRequest) {
        return User.builder()
                .username(userRequest.getUsername())
                .password(userRequest.getPassword())
                .nickname(userRequest.getNickname())
                .email(userRequest.getEmail())
                .role(userRequest.getRole())
                .build();
    }

    public static User from(UserResponse response) {
        return User.builder()
                .id(response.getUserId())
                .username(response.getUsername())
                .password(response.getPassword())
                .email(response.getEmail())
                .nickname(response.getNickname())
                .phoneNumber(response.getPhoneNumber())
                .bank(response.getBank())
                .account(response.getAccount())
                .accountHolder(response.getAccountHolder())
                .role(response.getRole())
                .isShareRoom(response.isShareRoom())
                .build();
    }

    public static User from(UserRequest request) {
        return User.builder()
                .name(request.getName())
                .username(request.getUsername())
                .password(request.getPassword())
                .nickname(request.getNickname())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .account(request.getAccount())
                .accountHolder(request.getAccountHolder())
                .bank(request.getBank())
                .role(request.getRole())
                .build();
    }


}
