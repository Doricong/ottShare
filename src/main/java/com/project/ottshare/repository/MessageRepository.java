package com.project.ottshare.repository;


import com.project.ottshare.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {

    Page<Message> findAllByOttShareRoomIdAndSharingUserId(Long roomId, Long userId, Pageable pageable);
}
