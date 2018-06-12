package uk.co.datadisk.ddflixjpa.repositories;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import uk.co.datadisk.ddflixjpa.DdflixJpaApplication;
import uk.co.datadisk.ddflixjpa.entities.Address;
import uk.co.datadisk.ddflixjpa.entities.City;
import uk.co.datadisk.ddflixjpa.entities.Country;
import uk.co.datadisk.ddflixjpa.entities.County;

import javax.persistence.EntityManager;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DdflixJpaApplication.class)
@TestPropertySource("classpath:application-test.properties")
public class AddressRepositoryTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

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
        Address address = new Address();
        address.setNumber("1");
        address.setName("The Willows");
        address.setStreet1("River Lane");
        address.setStreet2("Lake Estate");
        address.setPostcode("RL11 2RL");
        addressRepository.save(address);
    }

    @Test
    @Transactional
    public void readAddress(){
        // the create happens in the setUp method - see @Before
        assertEquals("The Willows", addressRepository.findByName("The Willows").getName());
        assertEquals("The Willows", addressRepository.findByNumberAndStreet1("1", "River Lane").getName());
    }

    @Test
    @Transactional
    public void deleteAddress(){
        Address address = addressRepository.findByName("The Willows");
        assertEquals("The Willows", address.getName());

        addressRepository.delete(address);
        assertNull(addressRepository.findByName("The Willows"));
    }

    @Test
    @Transactional
    public void updateAddress(){
        Address address = addressRepository.findByName("The Willows");
        assertEquals("The Willows", address.getName());

        address.setName("The Willows - updated");
        addressRepository.save(address);
        assertEquals("The Willows - updated", addressRepository.findByName("The Willows - updated").getName());
    }

    @Test
    @Transactional
    //@Rollback(false)
    public void addCityToAddress() {

        // Create Country
        Country country = new Country();
        country.setCountry("England");
        countryRepository.save(country);

        // Create County and set the country
        County county = new County();
        county.setCounty("Buckinghamshire");
        county.setCountry(country);
        countyRepository.save(county);

        // CReate City and set county
        City city = new City();
        city.setCity("Milton Keynes");
        city.setCounty(county);
        cityRepository.save(city);

        // Find address and set City
        Address address = addressRepository.findByName("The Willows");
        address.setCity(city);
        addressRepository.save(address);

        // flush changes to the database and refresh the county/city entities to pickup the new changes
        // the commit does not happen until after the test, hence why we do below
        em.flush();
        em.refresh(city);
        em.refresh(address);

        // city checks
        assertEquals(1, city.getAddresses().size());
        assertTrue(city.getAddresses().contains(address));

        // city check
        assertEquals("The Willows", address.getName());
        assertEquals("Milton Keynes", address.getCity().toString());
        assertEquals("Buckinghamshire", address.getCity().getCounty().toString());
        assertEquals("England", address.getCity().getCounty().getCountry().toString());

        logger.info("DEBUG");
    }
}