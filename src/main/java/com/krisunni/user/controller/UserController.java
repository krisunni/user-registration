package com.krisunni.user.controller;

import com.krisunni.user.domain.User;
import com.krisunni.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    @PostMapping("/users")
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

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getBuser(@PathVariable Long id) throws URISyntaxException {
        log.debug("REST request to get Buser : {}", id);
        Optional<User> user = userService.findOne(id);

        if (!user.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.created(new URI("/api/users/" + user.get().getId()))
                .body(user.get());
    }
}
