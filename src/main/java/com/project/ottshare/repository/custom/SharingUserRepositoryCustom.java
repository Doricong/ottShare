package com.project.ottshare.repository.custom;

import com.project.ottshare.entity.SharingUser;

import java.util.Optional;

public interface SharingUserRepositoryCustom {
    Optional<SharingUser> findUserByRoomIdAndUserId(Long roomId, Long userId);

    void deleteByOttShareRoomId(Long roomId);
}
