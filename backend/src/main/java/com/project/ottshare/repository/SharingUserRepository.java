package com.project.ottshare.repository;

import com.project.ottshare.entity.SharingUser;
import com.project.ottshare.repository.custom.SharingUserRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SharingUserRepository extends JpaRepository<SharingUser, Long>, SharingUserRepositoryCustom {

    Optional<SharingUser> findByUser_Id(Long userId);

}
