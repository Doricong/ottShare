package com.project.ottshare.service;

import com.project.ottshare.aop.DistributeLock;
import com.project.ottshare.dto.sharingUserDto.IsLeaderAndOttResponse;
import com.project.ottshare.dto.waitingUserDto.WaitingUserRequest;
import com.project.ottshare.dto.waitingUserDto.WaitingUserResponse;
import com.project.ottshare.entity.User;
import com.project.ottshare.entity.WaitingUser;
import com.project.ottshare.enums.OttType;
import com.project.ottshare.exception.OttLeaderNotFoundException;
import com.project.ottshare.exception.OttNonLeaderNotFoundException;
import com.project.ottshare.exception.UserNotFoundException;
import com.project.ottshare.repository.UserRepository;
import com.project.ottshare.repository.WaitingUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class WaitingUserService {

    private final WaitingUserRepository waitingUserRepository;
    private final UserRepository userRepository;

    /**
     * user 저장
     */
    @DistributeLock(key = "#waitingUserRequest.userInfo.username")
    public void createWaitingUser(WaitingUserRequest waitingUserRequest) {
        User user = userRepository.findByUsername(waitingUserRequest.getUserInfo().getUsername())
                .orElseThrow(() -> new UserNotFoundException("해당 유저를 찾을수 없습니다."));

        WaitingUser waitingUser = WaitingUser.from(waitingUserRequest, user);
        waitingUserRepository.save(waitingUser);
    }

    /**
     * user 삭제
     */
    @Transactional
    public void deleteUser(Long id) {
        WaitingUser waitingUser = waitingUserRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        waitingUserRepository.delete(waitingUser);
    }

    /**
     * user 삭제
     */
    @Transactional
    public void deleteUsers(List<WaitingUserResponse> waitingUserResponses) {
        waitingUserResponses.stream()
                .map(waitingUserResponse -> waitingUserRepository.findById(waitingUserResponse.getId())
                        .orElseThrow(() -> new UserNotFoundException(waitingUserResponse.getId())))
                .forEach(waitingUserRepository::delete);
    }

    public Optional<Long> getWaitingUserIdByUserId(Long userId) {
        return waitingUserRepository.findByUserId(userId)
                .map(WaitingUser::getId);
    }

    /**
     * 리더가 있는지 확인
     */
    public WaitingUserResponse getLeaderByOtt(OttType ott) {
        WaitingUser waitingUser = waitingUserRepository.findLeaderByOtt(ott)
                .orElseThrow(() -> new OttLeaderNotFoundException(ott));

        return WaitingUserResponse.from(waitingUser);
    }

    /**
     * 리더가 아닌 user가 모두 있는지 확인
     */
    public List<WaitingUserResponse> getNonLeaderByOtt(OttType ott) {
        int nonLeaderCount = getNonLeaderCountByOtt(ott);
        List<WaitingUser> waitingUsers = waitingUserRepository.findNonLeadersByOtt(ott, nonLeaderCount);
        //리더가 아닌 user가 모두 있는지 확인
        if (waitingUsers.size() != nonLeaderCount) {
            throw new OttNonLeaderNotFoundException(ott);
        }

        return waitingUsers.stream()
                .map(WaitingUserResponse::from)
                .collect(Collectors.toList());
    }

    public Optional<IsLeaderAndOttResponse> getWaitingUserIsLeaderAndOttByUserId(Long userId) {
        Optional<WaitingUser> waitingUser = waitingUserRepository.findByUserUserId(userId);
        return waitingUser.map(user -> new IsLeaderAndOttResponse(user.isLeader(), user.getOtt()));
    }

    private int getNonLeaderCountByOtt(OttType ott) {
        return switch (ott) {
            case NETFLIX -> 2;
            case WAVVE, TVING -> 3;
            default -> throw new IllegalArgumentException("Unsupported OttType: " + ott);
        };
    }
}
