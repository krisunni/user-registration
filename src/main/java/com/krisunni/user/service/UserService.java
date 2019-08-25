package com.krisunni.user.service;

import com.krisunni.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {
    User save(User User);

    Page<User> findAll(Pageable pageable);

    Optional<User> findOne(Long id);

    void delete(Long id);
}
