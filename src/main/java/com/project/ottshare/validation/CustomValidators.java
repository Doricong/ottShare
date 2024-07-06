package com.project.ottshare.validation;

import com.project.ottshare.dto.userDto.UserRequest;
import com.project.ottshare.repository.UserRepository;
import com.project.ottshare.security.auth.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

@Component
@RequiredArgsConstructor
public class CustomValidators{

    private final UsernameJoinValidator usernameJoinValidator;
    private final NicknameJoinValidator nicknameJoinValidator;

    private final UsernameModifyValidator usernameModifyValidator;
    private final NicknameModifyValidator nicknameModifyValidator;

    /**
     * 회원가입 아이디, 닉네임 중복 검증
     * @param userRequest
     * @param bindingResult
     */
    public void joinValidateAll(UserRequest userRequest, BindingResult bindingResult) {
        usernameJoinValidator.doValidate(userRequest, bindingResult);
        nicknameJoinValidator.doValidate(userRequest, bindingResult);
    }

    /**
     * 회원 수정 아이디, 닉네임 중복 검증
     * @param userRequest
     * @param bindingResult
     */
    public void modifyValidateAll(UserRequest userRequest, BindingResult bindingResult) {
        usernameModifyValidator.doValidate(userRequest, bindingResult);
        nicknameModifyValidator.doValidate(userRequest, bindingResult);
    }

    /**
     * 회원가입 아이디 중복 검증
     */
    @Component
    @RequiredArgsConstructor
    public static class UsernameJoinValidator extends AbstractValidator<UserRequest> {

        private final UserRepository userRepository;
        @Override
        protected void doValidate(UserRequest dto, Errors errors) {
            if (userRepository.existsByUsername(dto.getUsername())) {
                errors.rejectValue("username", "아이디 중복 오류", "이미 사용중인 아이디 입니다.");
            }
        }
    }

    /**
     * 회원가입 닉네임 중복 검증
     */
    @Component
    @RequiredArgsConstructor
    public static class NicknameJoinValidator extends AbstractValidator<UserRequest> {

        private final UserRepository userRepository;
        @Override
        protected void doValidate(UserRequest dto, Errors errors) {
            if (userRepository.existsByNickname(dto.getNickname())) {
                errors.rejectValue("nickname", "닉네임 중복 오류", "이미 사용중인 닉네임 입니다.");
            }
        }
    }

    /**
     * 회원 수정 아이디 중복 검증
     */
    @Component
    @RequiredArgsConstructor
    public static class UsernameModifyValidator extends AbstractValidator<UserRequest> {

        private final UserRepository userRepository;
        @Override
        protected void doValidate(UserRequest dto, Errors errors) {
            String currentUsername = getCurrentUsername();

            //회원 수정 시에는 현재 사용자와 수정 대상의 아이디가 다르고, 중복되는지 확인
            if (!dto.getUsername().equals(currentUsername) && userRepository.existsByUsername(dto.getUsername())) {
                errors.rejectValue("username", "아이디 중복 오류", "이미 사용중인 아이디 입니다.");
            }
        }
        private String getCurrentUsername() {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            return authentication.getName();
        }
    }

    /**
     * 회원 수정 닉네임 중복 검증
     */
    @Component
    @RequiredArgsConstructor
    public static class NicknameModifyValidator extends AbstractValidator<UserRequest> {

        private final UserRepository userRepository;

        @Override
        protected void doValidate(UserRequest dto, Errors errors) {
            String currentNickname = getCurrentNickname();

            //회원 수정 시에는 현재 사용자와 수정 대상의 닉네임이 다르고, 중복되는지 확인
            if (!dto.getNickname().equals(currentNickname) && userRepository.existsByNickname(dto.getNickname())) {
                errors.rejectValue("nickname", "닉네임 중복 오류", "이미 사용중인 닉네임 입니다.");
            }
        }
        private String getCurrentNickname() {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
                return ((CustomUserDetails) authentication.getPrincipal()).getNickname();
            }
            return null;
        }
    }
}
