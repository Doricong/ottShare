package com.project.ottshare.service.waitingUser;

import com.project.ottshare.dto.waitingUserDto.WaitingUserRequest;
import com.project.ottshare.dto.waitingUserDto.WaitingUserResponse;
import com.project.ottshare.enums.OttType;


import java.util.List;

public interface WaitingUserService {

    void save(WaitingUserRequest waitingUserRequest);

    void deleteUser(Long id);

    void deleteUsers(List<WaitingUserResponse> waitingUserResponses);

    boolean existsByUserId(Long userId);

    WaitingUserResponse getLeaderByOtt(OttType ott);

    List<WaitingUserResponse> getNonLeaderByOtt(OttType ott);
}
