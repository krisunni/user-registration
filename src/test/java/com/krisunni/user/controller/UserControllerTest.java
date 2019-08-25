package com.krisunni.user.controller;

import com.krisunni.user.TestUtil;
import com.krisunni.user.domain.User;
import com.krisunni.user.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Integration tests for the {@link UserController} REST controller.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {
    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";
    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";
    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";
    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;

    public User setInitialUser() {
        return User.setUser(DEFAULT_FIRST_NAME, DEFAULT_FIRST_NAME, DEFAULT_TELEPHONE, DEFAULT_EMAIL);
    }

    @Test
    public void usersShouldCreateUserAndReturnUser() throws Exception {
        User createUser = setInitialUser();
        when(userService.save(any())).thenReturn(createUser);
        this.mockMvc.perform(post("/api/users")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(createUser)))
                .andExpect(status().isCreated());

    }

    @Test
    public void createUserWithExistingIdShouldReturnBadRequest() throws Exception {
        User createUser = setInitialUser();
        createUser.setId(1L);
        this.mockMvc.perform(post("/api/users")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(createUser)))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void getUserWithIDShouldCallfindOne() throws Exception {
        User createUser = setInitialUser();
        createUser.setId(1L);
        when(userService.findOne(1L)).thenReturn(Optional.of(createUser));
        this.mockMvc.perform(get("/api/users/{id}", 1L)
                .contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id").value(createUser.getId().intValue()))
                .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
                .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
                .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE))
                .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL));
    }
}
