package uk.co.datadisk.ddflixjpa.repositories;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import uk.co.datadisk.ddflixjpa.DdflixJpaApplication;
import uk.co.datadisk.ddflixjpa.entities.*;
import uk.co.datadisk.ddflixjpa.entities.film.Film;
import uk.co.datadisk.ddflixjpa.entities.film.Wishlist;
import uk.co.datadisk.ddflixjpa.repositories.film.FilmRepository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

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

    @Autowired
    FilmRepository filmRepository;

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
    public void checkUniqueEmail(){
        assertNotNull(userRepository.findByEmail("paul.valle@example.com"));
        assertNull(userRepository.findByEmail("will.hay@example.com"));
    }

    @Test
    @Transactional
    //@Rollback(false)
    public void addAndDeleteShippingAddress() {

        // Create Country
        Country country = new Country();
        country.setCountry("England");

        // Create County and set the country
        County county = new County();
        county.setCounty("Buckinghamshire");
        county.setCountry(country);

        // Create City and set county
        City city = new City();
        city.setCity("Milton Keynes");
        city.setCounty(county);

        // Create address and set City
        Address address = new Address();
        address.setNumber("1");
        address.setName("The Willows");
        address.setStreet1("River Lane");
        address.setStreet2("Lake Estate");
        address.setPostcode("RL11 2RL");
        address.setCity(city);

        // Find the user and add a shipping address
        User user = userRepository.findByEmail("paul.valle@example.com");
        user.addShippingAddress(address);
        userRepository.save(user);

        // Address check
        assertEquals(1, user.getShippingAddresses().size());
        assertEquals("The Willows", user.getShippingAddresses().iterator().next().getName());

        user.removeShippingAddress(address);
        userRepository.save(user);

        assertEquals(0, user.getShippingAddresses().size());

        logger.info("DEBUG");
    }

    @Test
    @Transactional
    //@Rollback(false)
    public void addFilmToWishlist(){

        User user2 = User.builder().email("lorraine.valle@example.com").build();
        userRepository.save(user2);

        Film film1 = new Film();
        film1.setTitle("Alien");
        filmRepository.save(film1);

        Film film2 = new Film();
        film2.setTitle("Safe");
        filmRepository.save(film2);

        Film film3 = new Film();
        film3.setTitle("Outpost");
        filmRepository.save(film3);

        User user1 = userRepository.findByEmail("paul.valle@example.com");

        // there are multiple ways to do the same thing
        user1.getWishlists().add(new Wishlist(user1, film1));
        user1.getWishlists().add(new Wishlist(user1, film2));
        user1.getWishlists().add(new Wishlist(user1, film3));
        user2.addFilmToWishList(film2);
        user2.addFilmToWishList(film1);

        user1.getWishlists().remove(new Wishlist(user1,film1));
        user2.removeFilmFromWishlist(film2);

        // the below does indeed delete the entries in the wishlist table for only user2
        //userRepository.delete(user2);

        System.out.println("User " + user1.getEmail() + " wishlist size: " + user1.getWishlists().size());
        System.out.println("User " + user2.getEmail() + " wishlist size: " + user2.getWishlists().size());
        assertTrue(user1.getWishlists().size() == 2);
        assertTrue(user2.getWishlists().size() == 1);

        System.out.println("DEB INFO");
    }

    @Test
    @Transactional
    //@Rollback(false)
    public void checkFilmsInWishlist(){

        User user1 = userRepository.findByEmail("paul.valle@example.com");

        Film film1 = new Film();
        film1.setTitle("Alien");
        filmRepository.save(film1);

        Film film2 = new Film();
        film2.setTitle("Safe");
        filmRepository.save(film2);

        user1.addFilmToWishList(film1);
        user1.addFilmToWishList(film1);             // won't be added as we already have film1 in wishlist
        user1.removeFilmFromWishlist(film2);        // won't remove as we don't have film2 in wishlist
    }

    @Test
    @Transactional
    @DirtiesContext     // clean up the films from the persistent context
    //@Rollback(false)
    public void sortWishlistOrder() {
        User user1 = userRepository.findByEmail("paul.valle@example.com");

        Film film1 = new Film();
        film1.setTitle("Alien");
        filmRepository.save(film1);

        Film film2 = new Film();
        film2.setTitle("Safe");
        filmRepository.save(film2);

        Film film3 = new Film();
        film3.setTitle("Outpost");
        filmRepository.save(film3);

        user1.addFilmToWishList(film1);

        try {
            Thread.sleep(2000);
        } catch(Exception ex) {
            System.out.println(ex);
        }

        user1.addFilmToWishList(film2);

        try {
            Thread.sleep(2000);
        } catch(Exception ex) {
            System.out.println(ex);
        }
        user1.addFilmToWishList(film3);

        user1.getSortedWishlistDesc().forEach(e -> System.out.println("Email:"+ e.getUser().getEmail() +", Film: "+e.getFilm().getTitle() +", Wished On:"+e.getWishedOn()));
        user1.getSortedWishlistAsc().forEach(e -> System.out.println("Email:"+ e.getUser().getEmail() +", Film: "+e.getFilm().getTitle() +", Wished On:"+e.getWishedOn()));
    }

    @Test
    @Transactional
    //@Rollback(false)
    public void setDefaultShippingAddress() {

        // Create Country
        Country country = new Country();
        country.setCountry("England");

        // Create County and set the country
        County county = new County();
        county.setCounty("Buckinghamshire");
        county.setCountry(country);

        // Create City and set county
        City city = new City();
        city.setCity("Milton Keynes");
        city.setCounty(county);

        // Create address and set City
        Address address = new Address();
        address.setNumber("1");
        address.setName("The Willows");
        address.setStreet1("River Lane");
        address.setStreet2("Lake Estate");
        address.setPostcode("RL11 2RL");
        address.setCity(city);

        // Find the user and add a shipping address
        User user = userRepository.findByEmail("paul.valle@example.com");
        user.addShippingAddress(address);
        user.setDefault_shipping_address(address);
        user.setDefault_billing_address(address);
        userRepository.save(user);

        assertEquals("The Willows", user.getDefault_shipping_address().getName());
        assertEquals("The Willows", user.getDefault_billing_address().getName());

        System.out.println("DEBUG INFO");
    }

    @Test
    @Transactional
    //@Rollback(false)
    public void deleteSpecifiedUsers() {

        User user1 = User.builder().email("will.hay@example.com").build();
        userRepository.save(user1);
        User user2 = User.builder().email("graham.moffatt@example.com").build();
        userRepository.save(user2);
        User user3 = User.builder().email("moore.marriott@example.com").build();
        userRepository.save(user3);

        assertEquals(4, userRepository.findAll().size());

        Collection<Long> userIds = new ArrayList<>();
        userIds.add(2L);
        userIds.add(3L);

        userRepository.deleteUsersByIdIn(userIds);
        assertEquals(2, userRepository.findAll().size());
    }
}