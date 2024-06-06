package com.project.ottshare.repository;

import com.project.ottshare.entity.User;
import com.project.ottshare.security.auth.CustomUserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByNameAndPhoneNumber(String name, String phoneNumber);

    Optional<User> findByNameAndEmail(String name, String email);

    Optional<User> findByNameAndUsernameAndEmail(String name, String username, String email);

    Optional<User> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByNickname(String nickname);
}
