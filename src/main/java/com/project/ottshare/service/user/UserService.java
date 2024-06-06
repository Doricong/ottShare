package com.project.ottshare.service.user;

import com.project.ottshare.dto.userDto.*;
import com.project.ottshare.entity.User;

import java.util.Optional;

public interface UserService {

    Long save(UserRequest userRequest);

    UserResponse getUser(Long id);

    String getUsername(String name, String phoneNumber);

    void UserVerificationService(String name, String username, String email);

    void updateUser(UserSimpleRequest userSimpleRequest);

    void updatePassword(String name, String email, String password);

    void deleteUser(Long id);

    void sendSmsToFindEmail(VerifyCodeRequest verifyCodeRequest);

    void verifySms(CheckCodeRequest checkCodeRequest);

    boolean authenticateUser(String userDetailsPassword, String password);

    Optional<User> findUserByEmail(String email);
}
