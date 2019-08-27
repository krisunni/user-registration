package com.krisunni.user.repository;

import com.krisunni.user.domain.User;
import com.krisunni.user.domain.dto.MultiUserRequest;

import java.util.List;

public interface UserRepositoryCustom {
    List<User> findUserbyColumns(MultiUserRequest multiUserRequest);
}