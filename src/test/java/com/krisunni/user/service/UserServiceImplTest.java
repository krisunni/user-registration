package com.krisunni.user.service;

import com.krisunni.user.TestUtil;
import com.krisunni.user.domain.User;
import com.krisunni.user.domain.dto.MultiUserRequest;
import com.krisunni.user.repository.UserRepository;
import com.krisunni.user.repository.UserRepositoryCustom;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.krisunni.user.TestUtil.initialUser;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {
    @Autowired
    private UserService userService;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private UserRepositoryCustom userRepositoryCustom;

    User intialUser;

    @Before
    public void setUp() {
        intialUser = initialUser();
    }

    @Test
    public void save() {
        User savedUser = initialUser();
        savedUser.setId(1L);
        when(userRepository.save(intialUser)).thenReturn(savedUser);
        User save = userService.save(intialUser);
        assertThat(save).isNotNull();
        assertThat(save.getId()).isEqualTo(savedUser.getId());
    }

    @Test
    public void delete() {
        User savedUser = initialUser();
        savedUser.setId(1L);
        doNothing().when(userRepository).deleteById(1L);
        userService.delete(1L);
        Mockito.verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    public void findAll() {
        intialUser.setId(1L);
        Pageable firstPageWithTwoElements = PageRequest.of(0, 1);
        List<User> users = new ArrayList<>();
        users.add(intialUser);
        Page<User> pageOfUsers = new PageImpl(users);
        when(userService.findAll(firstPageWithTwoElements)).thenReturn(pageOfUsers);
        Page<User> findAll = userService.findAll(firstPageWithTwoElements);
        assertThat(findAll).isNotNull();
        assertThat(findAll.getTotalElements()).isEqualTo(1);
        assertThat(findAll.get().findFirst().get()).isEqualTo(intialUser);
    }

    @Test
    public void findOne() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(intialUser));
        Optional<User> userFromFindOne = userService.findOne(1L);
        assertThat(userFromFindOne.isPresent());
        assertThat(userFromFindOne.get()).isEqualTo(intialUser);
    }

    @Test
    public void findUserbyColumns() {
        MultiUserRequest multiUserRequest = TestUtil.getGenericMultiUser();
        List<User> users = new ArrayList<>();
        users.add(intialUser);
        when(userRepositoryCustom.findUserbyColumns(multiUserRequest)).thenReturn(users);
        List<User> filteredUser = userService.getFilteredUser(multiUserRequest);
        assertThat(filteredUser.size()).isEqualTo(1);
    }
}
