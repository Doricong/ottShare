package com.project.ottshare.repository.custom;

import com.project.ottshare.entity.User;

import java.util.Optional;

public interface UserRepositoryCustom {

    Optional<User> findByUsername(String username);

    Optional<User> findByNameAndUsernameAndEmail(String name, String username, String email);
}
