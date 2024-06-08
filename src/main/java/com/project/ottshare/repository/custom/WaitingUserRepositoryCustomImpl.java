package com.project.ottshare.repository.custom;

import com.project.ottshare.entity.WaitingUser;
import com.project.ottshare.enums.OttType;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.project.ottshare.entity.QWaitingUser.waitingUser;

@Repository
@RequiredArgsConstructor
public class WaitingUserRepositoryCustomImpl implements WaitingUserRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<WaitingUser> findLeaderByOtt(OttType ott) {
        WaitingUser result = queryFactory
                .selectFrom(waitingUser)
                .where(waitingUser.ott.eq(ott)
                        .and(waitingUser.isLeader.isTrue()))
                .orderBy(waitingUser.createdDate.asc())
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public List<WaitingUser> findNonLeadersByOtt(OttType ott, int limit) {
        List<WaitingUser> result = queryFactory
                .selectFrom(waitingUser)
                .where(waitingUser.ott.eq(ott)
                        .and(waitingUser.isLeader.isFalse()))
                .orderBy(waitingUser.createdDate.asc())
                .limit(limit)
                .fetch();

        return result;
    }

    @Override
    public Optional<WaitingUser> findByUserId(Long userId) {
        return Optional.ofNullable(
                queryFactory
                        .selectFrom(waitingUser)
                        .where(waitingUser.user.userId.eq(userId))
                        .fetchOne()
        );
    }
}
