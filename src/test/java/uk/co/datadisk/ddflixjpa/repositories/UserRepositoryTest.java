package uk.co.datadisk.ddflixjpa.repositories;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import uk.co.datadisk.ddflixjpa.DdflixJpaApplication;
import uk.co.datadisk.ddflixjpa.entities.User;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DdflixJpaApplication.class)
@TestPropertySource("classpath:application-test.properties")
public class UserRepositoryTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserRepository userRepository;

    @Before
    public void setUp() {
        User user1 = User.builder().email("paul.valle@example.com").build();
        userRepository.save(user1);
    }

    @Test
    @Transactional
    public void createUser() {
        User user2 = User.builder().email("lorraine.valle@example.com").build();
        userRepository.save(user2);

        List<User> users = userRepository.findAll();
        for(User user : users){
            System.out.println("User: " + user.getEmail() + " (" + user.getId() + ")");
        }
    }

    @Test
    @Transactional
    public void lastUpdatedTest() {
        User user = userRepository.findByEmail("paul.valle@example.com");
        user.setEmail("paul.valle@example.com - updated");

        try {
            Thread.sleep(5000);
        }
        catch (Exception ex){
            System.out.println("ERROR trying to sleep");
        }
        userRepository.save(user);
    }
}