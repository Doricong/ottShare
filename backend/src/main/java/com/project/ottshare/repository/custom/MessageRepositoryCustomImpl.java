package com.project.ottshare.repository.custom;

import com.project.ottshare.entity.QMessage;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MessageRepositoryCustomImpl implements MessageRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public void deleteByOttShareRoomId(Long roomId) {
        jpaQueryFactory.delete(QMessage.message1)
                .where(QMessage.message1.ottShareRoom.id.eq(roomId))
                .execute();
    }
}