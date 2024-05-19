package com.project.ottshare.repository;

import com.project.ottshare.entity.WaitingUser;
import com.project.ottshare.enums.OttType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.domain.Pageable;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface WaitingUserRepository extends JpaRepository<WaitingUser, Long> {

    Optional<WaitingUser> findWaitingUserByUserId(Long userId);

    @Query("SELECT w FROM WaitingUser w WHERE w.ott = :ott AND w.isLeader = true ORDER BY w.createdDate ASC")
    Optional<WaitingUser> findLeadersByOtt(@Param("ott") OttType ott);

    @Query("SELECT w FROM WaitingUser w WHERE w.ott = :ott AND w.isLeader = false ORDER BY w.createdDate ASC")
    List<WaitingUser> findNonLeadersByOtt(@Param("ott") OttType ott, Pageable pageable);
}
