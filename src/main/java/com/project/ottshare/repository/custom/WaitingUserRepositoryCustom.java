package com.project.ottshare.repository.custom;

import com.project.ottshare.entity.WaitingUser;
import com.project.ottshare.enums.OttType;

import java.util.List;
import java.util.Optional;

public interface WaitingUserRepositoryCustom {
    Optional<WaitingUser> findLeaderByOtt(OttType ott);
    List<WaitingUser> findNonLeadersByOtt(OttType ott, int limit);
}
