package com.project.ottshare.repository;

import com.project.ottshare.entity.SharingUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SharingUserRepository extends JpaRepository<SharingUser, Long> {

    Optional<SharingUser> findSharingUserById(Long id);

    Optional<SharingUser> findByUserUserId(Long userId);
}
