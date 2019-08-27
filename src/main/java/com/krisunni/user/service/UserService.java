package com.krisunni.user.service;

import com.krisunni.user.domain.User;
import com.krisunni.user.domain.dto.MultiUserRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User save(User User);

    Page<User> findAll(Pageable pageable);

    Optional<User> findOne(Long id);

    void delete(Long id);

    List<User> getFilteredUser(MultiUserRequest multiUserRequest);
}
