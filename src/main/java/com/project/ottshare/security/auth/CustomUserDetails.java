package com.project.ottshare.security.auth;

import com.project.ottshare.entity.User;
import com.project.ottshare.enums.BankType;
import com.project.ottshare.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class CustomUserDetails implements UserDetails, OAuth2User {

    private User user;
    private Map<String , Object> attributes;

    // 일반 로그인
    public CustomUserDetails(User user) {
        this.user = user;
    }

    // Oauth2 로그인
    public CustomUserDetails(User user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }

    public Long getId() {
        return user.getUserId();
    }

    public String getEmail() {
        return user.getEmail();
    }

    public String getNickname() {
        return user.getNickname();
    }

    public User getUser() {
        return user;
    }

    public String getPhoneNumber() {
        return user.getPhoneNumber();
    }

    public BankType getBank() {
        return user.getBank();
    }

    public String getAccount() {
        return user.getAccount();
    }

    public String getAccountHolder() {
        return user.getAccountHolder();
    }

    public Role getRole() {
        return user.getRole();
    }

    public boolean isShareRoom() {
        return user.isShareRoom();
    }

    @Override
    public String getName() {
        return user.getUsername();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collectors = new ArrayList<>();
        collectors.add(() -> "ROLE_" + user.getRole());

        return collectors;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}