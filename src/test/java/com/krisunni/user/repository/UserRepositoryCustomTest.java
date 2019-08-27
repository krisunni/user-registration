package com.krisunni.user.repository;

import com.krisunni.user.TestUtil;
import com.krisunni.user.domain.User;
import com.krisunni.user.domain.dto.MultiUserRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan("com.krisunni.user.repository")
public class UserRepositoryCustomTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRepositoryCustom userRepositoryCustom;
    private MultiUserRequest multiUserRequest;

    @Before
    public void setUp() {
        User initialUser = TestUtil.initialUser();
        User nullEmailUser = TestUtil.initialUser();
        User nullFirstNameUser = TestUtil.initialUser();
        User nullLastNameUser = TestUtil.initialUser();
        User nullTelephoneUser = TestUtil.initialUser();

        nullEmailUser.setEmail(null);
        nullTelephoneUser.setTelephone(null);
        nullLastNameUser.setLastName(null);
        nullFirstNameUser.setFirstName(null);

        List<User> loadDummyUsers = new ArrayList<>();
        loadDummyUsers.add(initialUser);
        loadDummyUsers.add(nullEmailUser);
        loadDummyUsers.add(nullTelephoneUser);
        loadDummyUsers.add(nullLastNameUser);
        loadDummyUsers.add(nullFirstNameUser);

        userRepository.saveAll(loadDummyUsers);
        multiUserRequest = TestUtil.getGenericMultiUser();
        multiUserRequest.setSize(1);
        multiUserRequest.setPage(0);
    }

    @Test
    public void sanityCheckToEnsureUsersAreBeingSavedToDB() {
        List<User> queryResult = userRepository.findAll();
        assertThat(queryResult.size()).isEqualTo(5);
        assertThat(queryResult.get(0)).isNotNull();
    }

    @Test
    public void getUserWithNotNullEmail() {
        List<User> queryResult = userRepositoryCustom.findUserbyColumns(multiUserRequest);
        assertThat(queryResult.size()).isEqualTo(1);
        assertThat(queryResult.get(0)).isNotNull();
    }

    @Test
    public void getUserWithNotNullTelephone() {
        List<User> queryResult = userRepositoryCustom.findUserbyColumns(multiUserRequest);
        assertThat(queryResult.size()).isEqualTo(1);
        assertThat(queryResult.get(0)).isNotNull();
    }

    @Test
    public void getUserWithNotNullFirstName() {
        List<User> queryResult = userRepositoryCustom.findUserbyColumns(multiUserRequest);
        assertThat(queryResult.size()).isEqualTo(1);
        assertThat(queryResult.get(0)).isNotNull();
    }

    @Test
    public void getUserWithNotNullLastName() {
        List<User> queryResult = userRepositoryCustom.findUserbyColumns(multiUserRequest);
        assertThat(queryResult.size()).isEqualTo(1);
        assertThat(queryResult.get(0)).isNotNull();
    }
}
