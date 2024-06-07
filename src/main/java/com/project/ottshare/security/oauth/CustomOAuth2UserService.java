package com.project.ottshare.security.oauth;

import com.project.ottshare.entity.User;
import com.project.ottshare.enums.Role;
import com.project.ottshare.repository.UserRepository;
import com.project.ottshare.security.auth.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        Map<String, Object> attributes = oAuth2User.getAttributes();

        //OAuth 2.0 인증을 통해 사용자 정보를 가져 옴
        OAuth2UserInfo userInfo = getOAuth2UserInfo(registrationId, attributes);

        //OAuth2UserInfo 객체를 기반으로 사용자를 생성
        User user = createUserFromUserInfo(userInfo);

        if (!userRepository.existsByEmail(user.getEmail())) {
            userRepository.save(user);
        }

        CustomUserDetails customUserDetails = new CustomUserDetails(user);

        return customUserDetails;


    }


    private OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {

        OAuth2UserInfo userInfo = null;

        if (registrationId.equals("google")) {
            userInfo = new GoogleUserInfo(attributes);
        } else if (registrationId.equals("facebook")) {
            userInfo = new FacebookUserInfo(attributes);
        } else if (registrationId.equals("naver")) {
            userInfo = new NaverUserInfo((Map<String, Object>) attributes.get("response"));
        }

        return userInfo;
    }

    private User createUserFromUserInfo(OAuth2UserInfo userInfo) {
        String username = userInfo.getProvider() + "_" + userInfo.getProviderId();
        String password = null;
        String nickname = userInfo.getName() + "_" + userInfo.getProviderId();
        String email = userInfo.getEmail();
        Role role = Role.SOCIAL;

        User user = new User(username, password, nickname, email, role);

        return user;
    }
}
