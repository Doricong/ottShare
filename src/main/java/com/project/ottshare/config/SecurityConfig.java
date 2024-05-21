package com.project.ottshare.config;

import com.project.ottshare.security.oauth.CustomOAuth2UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService oauth2UserService;

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 정적 리소스에 대한 요청은 보안 검사 안함
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    /**
     * 보안 설정
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("users/join", "/users/login", "/api/**","/api/ottRecQuestions/**", "/faqs", "/chat/**", "/websocket/**", "/app/**", "/topic/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/users/login")
                        .loginProcessingUrl("/loginProc")
                        .defaultSuccessUrl("/")
                )
                .oauth2Login(formLogin -> formLogin
                        .loginPage("/users/login")
                        .userInfoEndpoint(user -> user
                                .userService(oauth2UserService))
                        .defaultSuccessUrl("/", true)
                )
                .logout(logout -> logout
                        .logoutUrl("/users/logout")
                        .addLogoutHandler(((request, response, authentication) -> {
                            HttpSession session = request.getSession();
                            if (session != null) {
                                session.invalidate();
                            }
                        }))
                        .logoutSuccessUrl("/")
                        .permitAll()
                );

        return http.build();
    }
}
