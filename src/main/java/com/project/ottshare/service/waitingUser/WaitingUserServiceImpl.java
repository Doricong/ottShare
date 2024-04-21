package com.project.ottshare.service.waitingUser;

import com.project.ottshare.dto.userDto.UserInfo;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class WaitingUserServiceImpl implements WaitingUserService{

    private final WaitingUserRepository waitingUserRepository;
    private final UserRepository userRepository;

    /**
     * user 저장
     */
    @Override
    @Transactional
    public void saveUser(WaitingUserRequest waitingUserRequest) {
        User user = userRepository.findByUsername(waitingUserRequest.getUserInfo().getUsername())
                .orElseThrow(() -> new UserNotFoundException("해당 유저를 찾을수 없습니다."));
        //waitingUserRequest -> waitingUser
        WaitingUser waitingUser = waitingUserRequest.toEntity(user);
        //waitingUser 저장
        waitingUserRepository.save(waitingUser);
    }

    /**
     * user 삭제
     */
    @Override
    @Transactional
    public void deleteUser(Long id) {
        WaitingUser waitingUser = waitingUserRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        waitingUserRepository.delete(waitingUser);
    }

    /**
     * user 삭제
     */
    @Override
    @Transactional
    public void deleteUsers(List<WaitingUserResponse> waitingUserResponses) {
        for (WaitingUserResponse waitingUserResponse : waitingUserResponses) {
            WaitingUser waitingUser = waitingUserRepository.findById(waitingUserResponse.getUser().getId())
                    .orElseThrow(() -> new UserNotFoundException(waitingUserResponse.getUser().getId()));
            //waitingUser 삭제
            waitingUserRepository.delete(waitingUser);
        }
    }

    /**
     * 리더가 있는지 확인
     */
    @Override
    public WaitingUserResponse findLeaderByOtt(OttType ott) {
        WaitingUser waitingUser = waitingUserRepository.findLeadersByOtt(ott)
                .orElseThrow(() -> new OttLeaderNotFoundException(ott));

        WaitingUserResponse waitingUserResponse = new WaitingUserResponse(waitingUser);

        return waitingUserResponse;
    }

    /**
     * 리더가 아닌 user가 모두 있는지 확인
     */
    @Override
    public List<WaitingUserResponse> findNonLeaderByOtt(OttType ott) {
        int nonLeaderCount = getNonLeaderCountByOtt(ott);
        Pageable pageRequest = PageRequest.of(0, nonLeaderCount);

        List<WaitingUser> waitingUsers = waitingUserRepository.findNonLeadersByOtt(ott, pageRequest);

        //리더가 아닌 user가 모두 있는지 확인
        if (waitingUsers.size() != nonLeaderCount) {
            throw new OttNonLeaderNotFoundException(ott);
        }

        List<WaitingUserResponse> waitingUserResponses = new ArrayList<>();

        for (WaitingUser waitingUser : waitingUsers) {
            WaitingUserResponse waitingUserResponse = new WaitingUserResponse(waitingUser);
            waitingUserResponses.add(waitingUserResponse);
        }

        return waitingUserResponses;
    }

    private int getNonLeaderCountByOtt(OttType ott) {
        switch (ott) {
            case NETFLIX:
                return 2;
            case DISNEY_PLUS:
                return 3;
            case TVING:
                return 3;
            default:
                throw new IllegalArgumentException("Unsupported OttType: " + ott);
        }
    }
}
