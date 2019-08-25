package com.krisunni.user.controller;

import com.krisunni.user.TestUtil;
import com.krisunni.user.domain.User;
import com.krisunni.user.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.krisunni.user.TestUtil.DEFAULT_EMAIL;
import static com.krisunni.user.TestUtil.DEFAULT_FIRST_NAME;
import static com.krisunni.user.TestUtil.DEFAULT_LAST_NAME;
import static com.krisunni.user.TestUtil.DEFAULT_TELEPHONE;
import static com.krisunni.user.TestUtil.initialUser;
import static com.krisunni.user.TestUtil.updatedUser;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Integration tests for the {@link UserController} REST controller.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {


    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;

    @Test
    public void createUserShouldCreateUser() throws Exception {
        User createUser = initialUser();
        when(userService.save(any())).thenReturn(createUser);
        this.mockMvc.perform(post("/api/user")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(createUser)))
                .andExpect(status().isCreated());
    }

    @Test
    public void createUserWithExistingIdShouldReturnBadRequest() throws Exception {
        User createUser = initialUser();
        createUser.setId(1L);
        this.mockMvc.perform(post("/api/user")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(createUser)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getUserWithIDShouldCallfindOne() throws Exception {
        User createUser = initialUser();
        createUser.setId(1L);
        when(userService.findOne(1L)).thenReturn(Optional.of(createUser));
        this.mockMvc.perform(get("/api/user/{id}", 1L)
                .contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id").value(createUser.getId().intValue()))
                .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
                .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
                .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE))
                .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL));
    }

    @Test
    public void getAllUsers() throws Exception {
        User createUser = initialUser();
        createUser.setId(1L);
        Pageable firstPageWithTwoElements = PageRequest.of(0, 1);
        List<User> users = new ArrayList<>();
        users.add(createUser);
        Page<User> pageOfUsers = new PageImpl(users);
        when(userService.findAll(firstPageWithTwoElements)).thenReturn(pageOfUsers);
        this.mockMvc.perform(get("/api/user?page={page}&size={size}", 0, 1)
                .contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.[*].id").value(hasItem(createUser.getId().intValue())))
                .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
                .andExpect(jsonPath("$.[*].lastName").value(DEFAULT_LAST_NAME))
                .andExpect(jsonPath("$.[*].telephone").value(DEFAULT_TELEPHONE))
                .andExpect(jsonPath("$.[*].email").value(DEFAULT_EMAIL));
    }
    @Test
    public void updateUser() throws Exception {
        User beforeSaveUser = initialUser();
        beforeSaveUser.setId(1L);
        User updatedUser = updatedUser();
        updatedUser.setId(1L);

        when(userService.findOne(1L)).thenReturn(Optional.of(beforeSaveUser));
        this.mockMvc.perform(put("/api/user")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(beforeSaveUser)))
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id").value(beforeSaveUser.getId().intValue()))
                .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
                .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
                .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE))
                .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL));
    }
}
