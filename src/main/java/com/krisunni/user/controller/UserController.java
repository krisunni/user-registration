package com.krisunni.user.controller;

import com.krisunni.user.domain.User;
import com.krisunni.user.domain.dto.MultiUserRequest;
import com.krisunni.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) throws URISyntaxException {
        log.debug("Post Request to save User: {},", user);
        if (user.getId() != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User savedUser = userService.save(user);
        if (savedUser == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
        return ResponseEntity.created(new URI("/api/users/" + savedUser.getId()))
                .body(savedUser);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getBuser(@PathVariable Long id) throws URISyntaxException {
        log.debug("REST request to get User : {}", id);
        Optional<User> user = userService.findOne(id);

        if (!user.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.created(new URI("/api/users/" + user.get().getId()))
                .body(user.get());
    }

    @GetMapping("/user")
    public ResponseEntity<List<User>> getAllUsers(@Valid Pageable pageable) {
        log.debug("REST request to get Users with Pageable: {}", pageable.toString());
        Page<User> page = userService.findAll(pageable);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", Long.toString(page.getTotalElements()));
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PutMapping("/user")
    public ResponseEntity<User> updateUser(@Valid @RequestBody User user) throws URISyntaxException {
        log.debug("Put Request to update User: {},", user);
        if (user.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User result = userService.save(user);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) throws URISyntaxException {
        log.debug("Put Request to delete User ID: {},", id);
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/multiuser")
    public ResponseEntity<List<User>> getFilteredUser(@Valid @RequestBody MultiUserRequest multiUserRequest) throws URISyntaxException {
        log.debug("Get Request for MultiUserRequest: {},", multiUserRequest);
        List<User> result = userService.getFilteredUser(multiUserRequest);
        return ResponseEntity.ok().body(result);
    }
}
