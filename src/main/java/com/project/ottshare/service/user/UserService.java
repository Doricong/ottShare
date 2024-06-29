package com.project.ottshare.service.user;

import com.project.ottshare.dto.userDto.*;
import com.project.ottshare.entity.User;

import java.util.Optional;

public interface UserService {

    void createUser(UserRequest userRequest);

    UserResponse getUser(Long id);

    String getUsername(String name, String phoneNumber);

    UserResponse findUserForPasswordReset(String name, String username, String email);

    void updateUser(UserSimpleRequest userSimpleRequest);

    void updatePassword(Long userId, String newPassword);

    void deleteUser(Long id);

    void sendSmsToFindEmail(VerifyCodeRequest verifyCodeRequest);

    void verifySms(CheckCodeRequest checkCodeRequest);

    boolean authenticateUser(String userDetailsPassword, String password);

    Optional<User> findUserByEmail(String email);
}
