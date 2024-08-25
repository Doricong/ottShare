package com.project.ottshare.repository;


import com.project.ottshare.entity.Message;
import com.project.ottshare.repository.custom.MessageRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long>, MessageRepositoryCustom {

    Page<Message> findAllByOttShareRoomIdOrderByCreatedDate(Long roomId, Pageable pageable);
}
