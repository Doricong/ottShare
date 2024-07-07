package com.project.ottshare.controller;

import com.project.ottshare.dto.userDto.*;
import com.project.ottshare.entity.User;
import com.project.ottshare.enums.Role;
import com.project.ottshare.security.auth.CustomUserDetails;
import com.project.ottshare.security.auth.CustomUserDetailsService;
import com.project.ottshare.service.UserService;
import com.project.ottshare.util.JwtUtil;
import com.project.ottshare.validation.CustomValidators;
import com.project.ottshare.validation.ValidationSequence;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Slf4j
public class UserApiController {

    private final UserService userService;
    private final CustomUserDetailsService customUserDetailsService;
    private final CustomValidators validators;
    private final JwtUtil jwtUtil;

    /**
     * 로그아웃
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok().body("로그아웃 되었습니다.");
    }

    /**
     * 회원가입
     */
    @PostMapping
    public ResponseEntity<?> registerUser(@Validated(ValidationSequence.class) @RequestBody UserRequest dto,
                                      BindingResult bindingResult) {
        validators.joinValidateAll(dto, bindingResult);
        if (bindingResult.hasErrors()) {
            return buildValidationErrorResponse(bindingResult);
        }
        UserResponse userResponse = userService.createUser(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    /**
     * 마이페이지
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserProfile(@PathVariable("userId") Long userId) {
        UserResponse userResponse = userService.getUser(userId);

        return ResponseEntity.ok(userResponse);
    }

    /**
     * 회원정보 수정
     */
    @GetMapping("/{userId}/edit")
    public ResponseEntity<UserResponse> getUpdateUserProfilePage(@PathVariable("userId") Long userId) {
        UserResponse user = userService.getUser(userId);

        return ResponseEntity.ok(user);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<?> updateUserProfile(@PathVariable("userId") Long id,
                                    @Validated(ValidationSequence.class) @RequestBody UserSimpleRequest dto,
                                    BindingResult bindingResult) {
//        validators.modifyValidateAll(userRequest, bindingResult);
        if (bindingResult.hasErrors()) {
            return buildValidationErrorResponse(bindingResult);
        }
        //회원 수정
        userService.updateUser(dto);

        return ResponseEntity.ok("User updated successfully");
    }

    /**
     * 회원 탈퇴
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable("userId") Long id) {
        userService.deleteUser(id);

        return ResponseEntity.ok("User Deleted successfully");
    }

    /**
     * 인증번호 전송
     */
    @PostMapping("/send-verification-code")
    public ResponseEntity<String> sendVerificationCode(@Validated(ValidationSequence.class) @RequestBody VerifyCodeRequest dto) {
        userService.sendSmsToFindEmail(dto);

        return ResponseEntity.ok("SMS가 성공적으로 전송되었습니다.");
    }

    /**
     * 인증번호 확인 -> 아이디 찾기
     */
    @PostMapping("/find-username")
    public ResponseEntity<Object> verifyCodeAndFindUsername(@Valid @RequestBody CheckCodeRequest dto) {
        userService.verifySms(dto);
        String username = userService.getUsername(dto.getName(), dto.getPhoneNumber());

        FindUsernameResponse response = new FindUsernameResponse("아이디는 " + username + "입니다.");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * 비밀번호 찾기
     */
    @PostMapping("/find-password")
    public ResponseEntity<Object> resetPassword(@Validated(ValidationSequence.class) @RequestBody FindPasswordRequest dto) {
        UserResponse userResponse = userService.findUserForPasswordReset(dto.getName(), dto.getUsername(), dto.getEmail());
        String temporaryPassword = PasswordGenerator.generatePassword(10);
        userService.updatePassword(userResponse.getUserId(), temporaryPassword);
        FindPasswordResponse response = new FindPasswordResponse("임시 비밀번호는 " + temporaryPassword + "입니다.");

        return ResponseEntity.ok(response);
    }

    /**
     * 구글 소셜 로그인
     */
    @PostMapping("/google-login")
    public ResponseEntity<?> loginWithGoogle(@RequestBody UserInfo userInfo) {
        return handleSocialLogin(userInfo);
    }

    /**
     * 카카오 소셜 로그인
     */
    @PostMapping("/kakao-login")
    public ResponseEntity<?> loginWithKakao(@RequestBody UserInfo userInfo) {
        return handleSocialLogin(userInfo);
    }

    private ResponseEntity<?> buildValidationErrorResponse(BindingResult bindingResult) {
        List<String> errorMessages = bindingResult.getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        return ResponseEntity.badRequest().body(errorMessages);
    }

    private ResponseEntity<?> handleSocialLogin(UserInfo userInfo) {
        try {
            CustomUserDetails userDetails = (CustomUserDetails) customUserDetailsService.loadUserByUsername(userInfo.getEmail());
            User user = userDetails.getUser();

            if (user.getRole() == Role.SOCIAL) {
                return generateJwtResponse(user.getUsername());
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 이메일입니다. 일반 로그인을 사용하세요.");
            }
        } catch (UsernameNotFoundException e) {
            UserResponse newUserResponse = userService.createUser(new UserRequest(userInfo));
            return generateJwtResponse(newUserResponse.getUsername());
        }
    }

    private ResponseEntity<JwtResponse> generateJwtResponse(String username) {
        String token = jwtUtil.generateToken(username);
        Long expiresIn = jwtUtil.getExpiration();
        JwtResponse jwtResponse = new JwtResponse(token, username, expiresIn);
        return ResponseEntity.ok(jwtResponse);
    }
}