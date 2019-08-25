package com.krisunni.user.controller;

import com.krisunni.user.TestUtil;
import com.krisunni.user.domain.User;
import com.krisunni.user.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
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
import static com.krisunni.user.TestUtil.UPDATED_EMAIL;
import static com.krisunni.user.TestUtil.UPDATED_FIRST_NAME;
import static com.krisunni.user.TestUtil.UPDATED_LAST_NAME;
import static com.krisunni.user.TestUtil.UPDATED_TELEPHONE;
import static com.krisunni.user.TestUtil.initialUser;
import static com.krisunni.user.TestUtil.updatedUser;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
    User intialUser;

    @Before
    public void setUp() {
        intialUser = initialUser();
    }

    @Test
    public void create() throws Exception {
        when(userService.save(any())).thenReturn(intialUser);
        this.mockMvc.perform(post("/api/user")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(intialUser)))
                .andExpect(status().isCreated());
    }

    @Test
    public void read() throws Exception {
        intialUser.setId(1L);
        when(userService.findOne(1L)).thenReturn(Optional.of(intialUser));
        this.mockMvc.perform(get("/api/user/{id}", 1L)
                .contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id").value(intialUser.getId().intValue()))
                .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
                .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
                .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE))
                .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL));
    }

    @Test
    public void updateUser() throws Exception {
        intialUser.setId(1L);
        User updatedUser = updatedUser();
        updatedUser.setId(1L);

        when(userService.save(intialUser)).thenReturn(updatedUser);
        this.mockMvc.perform(put("/api/user")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(intialUser)))
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id").value(intialUser.getId().intValue()))
                .andExpect(jsonPath("$.firstName").value(UPDATED_FIRST_NAME))
                .andExpect(jsonPath("$.lastName").value(UPDATED_LAST_NAME))
                .andExpect(jsonPath("$.telephone").value(UPDATED_TELEPHONE))
                .andExpect(jsonPath("$.email").value(UPDATED_EMAIL));
    }

    @Test
    public void deleteUser() throws Exception {
        intialUser.setId(1L);
        Mockito.doNothing().when(userService).delete(1L);
        this.mockMvc.perform(delete("/api/user/{id}",intialUser.getId())
                .contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isNoContent());
    }

    @Test
    public void createException() throws Exception {
        intialUser.setId(1L);
        this.mockMvc.perform(post("/api/user")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(intialUser)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void readAll() throws Exception {
        intialUser.setId(1L);
        Pageable firstPageWithTwoElements = PageRequest.of(0, 1);
        List<User> users = new ArrayList<>();
        users.add(intialUser);
        Page<User> pageOfUsers = new PageImpl(users);
        when(userService.findAll(firstPageWithTwoElements)).thenReturn(pageOfUsers);
        this.mockMvc.perform(get("/api/user?page={page}&size={size}", 0, 1)
                .contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.[*].id").value(hasItem(intialUser.getId().intValue())))
                .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
                .andExpect(jsonPath("$.[*].lastName").value(DEFAULT_LAST_NAME))
                .andExpect(jsonPath("$.[*].telephone").value(DEFAULT_TELEPHONE))
                .andExpect(jsonPath("$.[*].email").value(DEFAULT_EMAIL));
    }
}
