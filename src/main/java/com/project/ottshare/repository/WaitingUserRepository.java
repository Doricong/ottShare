package com.project.ottshare.repository;

import com.project.ottshare.entity.WaitingUser;
import com.project.ottshare.repository.custom.WaitingUserRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WaitingUserRepository extends JpaRepository<WaitingUser, Long>, WaitingUserRepositoryCustom {

    Optional<WaitingUser> findByUser_Id(Long userId);
}
