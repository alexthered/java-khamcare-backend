package com.khamcare.app.repository;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.khamcare.app.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserRepositoryTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
        FixtureFactoryLoader.loadTemplates("com.khamcare.app.fixture");
    }

    @Test
    public void testFindUserByEmail() throws Exception {
        // given
        User user = Fixture.from(User.class).gimme("valid");
        testEntityManager.persist(user);
        testEntityManager.flush();

        // when
        User found = userRepository.findUserByEmail(user.getEmail());

        // then
        assertValidUser(found, user);
    }

    @Test
    public void testFindUserById() throws Exception {
        User user = Fixture.from(User.class).gimme("valid");

        User savedUser = userRepository.save(user);

        User foundUser = userRepository.findOne(user.getId());

        assertNotNull("foundUser must not be null", foundUser);
        assertValidUser(foundUser, user);
    }

    @Test
    public void testUpdateUser() throws Exception {
        // given
        User user = Fixture.from(User.class).gimme("valid");
        testEntityManager.persist(user);
        testEntityManager.flush();

        user.setFirstName("My new first name");
        User updatedUser = userRepository.save(user);

        assertValidUser(updatedUser, user);
    }

    @Test
    public void testSaveUser() throws Exception {
        User user = Fixture.from(User.class).gimme("valid");

        User savedUser = userRepository.save(user);

        assertValidUser(savedUser, user);
    }

    @Test
    public void testDeleteUser() throws Exception {
        User user = Fixture.from(User.class).gimme("valid");

        User savedUser = userRepository.save(user);

        userRepository.delete(savedUser);

        assertEquals("there is no user left", 0, userRepository.count());
        assertNull("there is no user with that Id", userRepository.findOne(savedUser.getId()));
    }

    private void assertValidUser(User found, User expected){
        assertNotNull("found user must not non-null Id", found.getId());
        assertEquals("found user must have same first name", expected.getFirstName(), found.getFirstName());
        assertEquals("found user must have same last name", expected.getLastName(), found.getLastName());
        assertEquals("found user must have same password", expected.getPassword(), found.getPassword());
    }
}