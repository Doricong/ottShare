package com.project.ottshare.repository.custom;

import com.project.ottshare.entity.QUser;
import com.project.ottshare.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.project.ottshare.entity.QSharingUser.sharingUser;
import static com.project.ottshare.entity.QUser.user;
import static com.project.ottshare.entity.QWaitingUser.waitingUser;

@RequiredArgsConstructor
@Repository
public class UserRepositoryCustomImpl implements UserRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<User> findByUsername(String username) {
        User result = queryFactory
                .selectFrom(user)
                .leftJoin(user.sharingUser, sharingUser).fetchJoin()
                .leftJoin(user.waitingUser, waitingUser).fetchJoin()
                .where(user.username.eq(username))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public Optional<User> findByNameAndUsernameAndEmail(String name, String username, String email) {
        User result = queryFactory.selectFrom(user)
                .leftJoin(user.sharingUser, sharingUser).fetchJoin()
                .leftJoin(user.waitingUser, waitingUser).fetchJoin()
                .where(
                        user.name.eq(name)
                        .and(user.username.eq(username))
                        .and(user.email.eq(email))
                )
                .fetchOne();

        return Optional.ofNullable(result);
    }
}
