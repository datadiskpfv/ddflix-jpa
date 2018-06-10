package uk.co.datadisk.ddflixjpa.repositories;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import uk.co.datadisk.ddflixjpa.DdflixJpaApplication;
import uk.co.datadisk.ddflixjpa.entities.User;

import javax.persistence.EntityManager;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DdflixJpaApplication.class)
public class UserRepositoryTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    EntityManager em;

    @Autowired
    UserRepository userRepository;

    @Before
    public void setUp() {
        User user1 = User.builder().email("paul.valle@example.com").build();
        userRepository.saveAndFlush(user1);
    }

    @Test
    @Transactional
    @Rollback(false)
    public void createUser() {
        //User user1 = User.builder().email("paul.valle@example.com").build();
        User user2 = User.builder().email("lorraine.valle@example.com").build();
        //userRepository.saveAndFlush(user1);
        userRepository.saveAndFlush(user2);

        List<User> users = userRepository.findAll();
        logger.info("User list:" + users.toString());
    }
}