package com.project.ottshare.controller;

import com.project.ottshare.dto.userDto.*;
import com.project.ottshare.entity.User;
import com.project.ottshare.enums.Role;
import com.project.ottshare.security.auth.CustomUserDetails;
import com.project.ottshare.security.auth.CustomUserDetailsService;
import com.project.ottshare.service.user.UserService;
import com.project.ottshare.validation.CustomValidators;
import com.project.ottshare.validation.ValidationSequence;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Slf4j
public class UserApiController {

    private final UserService userService;
    private final CustomUserDetailsService customUserDetailsService;
    private final CustomValidators validators;

    @PostMapping("/loginProc")
    public ResponseEntity<?> login(@RequestBody LoginUserRequest user) {
        // 입력받은 아이디로 사용자 조회
        CustomUserDetails userDetails = (CustomUserDetails) customUserDetailsService.loadUserByUsername(user.getUsername());
        // 비밀번호 검증
        if (!userService.authenticateUser(userDetails.getPassword(), user.getPassword())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("잘못된 비밀번호입니다.");
        }
        UserInfo userInfo = new UserInfo(userDetails);

        return ResponseEntity.ok(userInfo);
    }

    /**
     * 로그아웃
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return ResponseEntity.ok().body("로그아웃 되었습니다.");
    }

    /**
     * 회원가입
     */
    @PostMapping("/join")
    public ResponseEntity<?> joinUser(@Validated(ValidationSequence.class) @RequestBody UserRequest dto,
                                      BindingResult bindingResult) {
        //유효성 검사
        validators.joinValidateAll(dto, bindingResult);
        if (bindingResult.hasErrors()) {
            // 유효성 검사 실패 시 오류 메시지 반환
            List<String> errorMessages = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());

            return ResponseEntity.badRequest().body(errorMessages);
        }

        // 회원 저장
        userService.save(dto);

        return ResponseEntity.ok("User registered successfully");
    }

    /**
     * 마이페이지
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> myPage(@PathVariable("userId") Long userId) {
        UserResponse userResponse = userService.getUser(userId);

        return ResponseEntity.ok(userResponse);
    }

    /**
     * 회원정보 수정
     */
    @GetMapping("/{userId}/modification")
    public ResponseEntity<UserResponse> modify(@PathVariable("userId") Long userId) {
        UserResponse user = userService.getUser(userId);

        return ResponseEntity.ok(user);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<?> modify(@PathVariable("userId") Long id,
                                    @Validated(ValidationSequence.class) @RequestBody UserSimpleRequest dto,
                                    BindingResult bindingResult) {
        UserRequest userRequest = new UserRequest(dto.getUsername(), dto.getPassword(), dto.getNickname());

        //유효성 검사
        validators.modifyValidateAll(userRequest, bindingResult);

        if (bindingResult.hasErrors()) {
            // 모든 오류 메시지를 반환
            List<String> errorMessages = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errorMessages);
        }
        //회원 수정
        userService.updateUser(dto);

        // 변경된 세션 등록
        Authentication authentication = new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
    }

    /**
     * 회원 탙퇴
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> delete(@PathVariable("userId") Long id) {
        userService.deleteUser(id);

        return new ResponseEntity<>("User Deleted successfully", HttpStatus.OK);
    }

    /**
     * 인증번호 전송
     */
    @PostMapping("/send")
    public ResponseEntity<String> findUsername(@Validated(ValidationSequence.class) @RequestBody VerifyCodeRequest dto) {
        userService.sendSmsToFindEmail(dto);

        return ResponseEntity.ok("SMS가 성공적으로 전송되었습니다.");
    }

    /**
     * 인증번호 확인 -> 아이디 찾기
     */
    @PostMapping("/find-username")
    public ResponseEntity<Object> verifyVerificationCode(@Valid @RequestBody CheckCodeRequest dto) {
        userService.verifySms(dto);
        // 사용자 아이디 찾기
        String username = userService.getUsername(dto.getName(), dto.getPhoneNumber());

        FindUsernameResponse response = new FindUsernameResponse("아이디는 " + username + "입니다.");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * 비밀번호 찾기
     */
    @PostMapping("/find-password")
    public ResponseEntity<Object> findPassword(@Validated(ValidationSequence.class) @RequestBody FindPasswordRequest dto) {
        String name = dto.getName();
        String username = dto.getUsername();
        String email = dto.getEmail();

        // 이름, 아이디, 이메일이 모두 일치하는지 확인
        userService.UserVerificationService(name, username, email);

        String temporaryPassword = PasswordGenerator.generatePassword(10);

        FindPasswordResponse response = new FindPasswordResponse("임시 비밀번호는" + temporaryPassword + "입니다.");

        // 임시 비밀번호로 변경
        userService.updatePassword(name, email, temporaryPassword);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/google-login")
    public ResponseEntity<?> googleLogin(@RequestBody UserInfo userInfo, HttpServletRequest request) {
        String email = userInfo.getEmail();
        Optional<User> existingUser = userService.findUserByEmail(email);

        if (existingUser.isPresent()) {
            if (existingUser.get().getRole() == Role.SOCIAL) {
                return ResponseEntity.ok(userInfo);
            }
            // 기존 사용자가 존재할 경우, 일반 로그인 사용자인지 확인합니다.
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 이메일입니다. 일반 로그인을 사용하세요.");
        } else {
            // 사용자가 존재하지 않으면 회원가입 처리
            userService.save(userInfo.toUserRequest());
            CustomUserDetails userDetails = (CustomUserDetails) customUserDetailsService.loadUserByUsername(email);

            // 세션에 사용자 정보 저장
//            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());

            return ResponseEntity.ok(new UserInfo(userDetails.getUser()));
        }
    }

}
