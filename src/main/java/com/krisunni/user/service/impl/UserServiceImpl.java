package com.krisunni.user.service.impl;


import com.krisunni.user.domain.User;
import com.krisunni.user.repository.UserRepository;
import com.krisunni.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link User}.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    /**
     * Save a user.
     *
     * @param user the entity to save.
     * @return the persisted entity.
     */
    @Override
    public User save(User user) {
        log.debug("Request to save User : {}", user);
        return userRepository.save(user);
    }

    /**
     * Get all the users.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    @Override
    public Page<User> findAll(Pageable pageable) {
        log.debug("Request to get all Users");
        Page<User> all = userRepository.findAll(pageable);
        return all;
    }

    /**
     * Get one user by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<User> findOne(Long id) {
        log.debug("Request to get User : {}", id);
        return userRepository.findById(id);
    }

    /**
     * Delete the user by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete User : {}", id);
        userRepository.deleteById(id);

    }
}
