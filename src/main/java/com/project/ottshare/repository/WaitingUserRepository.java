package com.project.ottshare.repository;

import com.project.ottshare.entity.WaitingUser;
import com.project.ottshare.enums.OttType;
import com.project.ottshare.repository.custom.WaitingUserRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.domain.Pageable;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface WaitingUserRepository extends JpaRepository<WaitingUser, Long>, WaitingUserRepositoryCustom {
}
