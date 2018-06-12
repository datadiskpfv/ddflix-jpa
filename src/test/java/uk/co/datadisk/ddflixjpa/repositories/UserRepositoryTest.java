package uk.co.datadisk.ddflixjpa.repositories;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import uk.co.datadisk.ddflixjpa.DdflixJpaApplication;
import uk.co.datadisk.ddflixjpa.entities.*;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DdflixJpaApplication.class)
@TestPropertySource("classpath:application-test.properties")
public class UserRepositoryTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserRepository userRepository;

    @Autowired
    EntityManager em;

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    CountyRepository countyRepository;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    AddressRepository addressRepository;

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
    public void deleteUser(){
        User user = userRepository.findByEmail("paul.valle@example.com");
        assertEquals("paul.valle@example.com", user.getEmail());

        userRepository.delete(user);
        assertNull(userRepository.findByEmail("paul.valle@example.com"));
    }

    @Test
    @Transactional
    public void updateUser(){
        User user = userRepository.findByEmail("paul.valle@example.com");
        assertEquals("paul.valle@example.com", user.getEmail());

        user.setEmail("paul.valle@example.com - updated");
        userRepository.save(user);
        assertEquals("paul.valle@example.com - updated", userRepository.findByEmail("paul.valle@example.com - updated").getEmail());
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

    @Test
    @Transactional
    @Rollback(false)
    public void addCityToAddress() {

        // Create Country
        Country country = new Country();
        country.setCountry("England");
        //countryRepository.save(country);      // Managed by cascade

        // Create County and set the country
        County county = new County();
        county.setCounty("Buckinghamshire");
        county.setCountry(country);
        //countyRepository.save(county);        // Managed by cascade

        // Create City and set county
        City city = new City();
        city.setCity("Milton Keynes");
        city.setCounty(county);
        //cityRepository.save(city);            // Managed by cascade

        // Create address and set City
        Address address = new Address();
        address.setNumber("1");
        address.setName("The Willows");
        address.setStreet1("River Lane");
        address.setStreet2("Lake Estate");
        address.setPostcode("RL11 2RL");
        address.setCity(city);
        //addressRepository.save(address);      // Managed by cascade

        // Find the user and add a shipping address
        User user = userRepository.findByEmail("paul.valle@example.com");
        user.addShippingAddress(address);
        userRepository.save(user);

        // flush changes to the database and refresh the county/city entities to pickup the new changes
        // the commit does not happen until after the test, hence why we do below
        em.flush();
        em.refresh(user);

        // Address check
        //assertEquals("paul.valle@example.com", address.getUser().getEmail());

        logger.info("DEBUG");
    }
}