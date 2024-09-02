package com.project.ottshare.repository.custom;

import com.project.ottshare.entity.SharingUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.project.ottshare.entity.QSharingUser.*;

@Repository
@RequiredArgsConstructor
public class SharingUserRepositoryCustomImpl implements SharingUserRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public Optional<SharingUser> findUserByRoomIdAndUserId(Long roomId, Long userId) {
        SharingUser result = jpaQueryFactory.selectFrom(sharingUser)
                .where(sharingUser.ottShareRoom.id.eq(roomId)
                        .and(sharingUser.user.id.eq(userId)))
                .fetchOne();
        return Optional.ofNullable(result);

    }

    @Override
    public void deleteByOttShareRoomId(Long roomId) {
        jpaQueryFactory.delete(sharingUser)
                .where(sharingUser.ottShareRoom.id.eq(roomId))
                .execute();
    }
}
