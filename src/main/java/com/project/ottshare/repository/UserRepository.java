package com.project.ottshare.repository;

import com.project.ottshare.entity.User;
import com.project.ottshare.repository.custom.UserRepositoryCustom;
import com.project.ottshare.security.auth.CustomUserDetails;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

    Optional<User> findByNameAndPhoneNumber(String name, String phoneNumber);

    Optional<User> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByNickname(String nickname);
}
