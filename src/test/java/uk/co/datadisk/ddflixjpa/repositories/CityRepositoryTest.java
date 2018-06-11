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
import uk.co.datadisk.ddflixjpa.entities.City;
import uk.co.datadisk.ddflixjpa.entities.Country;
import uk.co.datadisk.ddflixjpa.entities.County;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DdflixJpaApplication.class)
@TestPropertySource("classpath:application-test.properties")
public class CityRepositoryTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    EntityManager em;

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    CountyRepository countyRepository;

    @Autowired
    CityRepository cityRepository;

    @Before
    public void setUp() throws Exception {

        // Create a new city
        City city = new City();
        city.setCity("Milton Keynes");
        cityRepository.save(city);
    }

    @Test
    @Transactional
    public void readCity(){
        // the create happens in the setUp method - see @Before
        assertEquals("Milton Keynes", cityRepository.findByCity("Milton Keynes").getCity());
    }

    @Test
    @Transactional
    public void deleteCity(){
        City city = cityRepository.findByCity("Milton Keynes");
        assertEquals("Milton Keynes", city.getCity());

        cityRepository.delete(city);
        assertNull(cityRepository.findByCity("Milton Keynes"));
    }

    @Test
    @Transactional
    public void updateCity(){
        City city = cityRepository.findByCity("Milton Keynes");
        assertEquals("Milton Keynes", city.getCity());

        city.setCity("Milton Keynes - updated");
        cityRepository.save(city);
        assertEquals("Milton Keynes - updated", cityRepository.findByCity("Milton Keynes - updated").getCity());
    }

    @Test
    @Transactional
    //@Rollback(false)
    public void addCityToCounty() {

        // Create Country
        Country country = new Country();
        country.setCountry("England");
        countryRepository.save(country);

        // Create County and set the country
        County county = new County();
        county.setCounty("Buckinghamshire");
        county.setCountry(country);
        countyRepository.save(county);

        City city = cityRepository.findByCity("Milton Keynes");
        city.setCounty(county);
        cityRepository.save(city);

        // flush changes to the database and refresh the county/city entities to pickup the new changes
        // the commit does not happen until after the test, hence why we do below
        em.flush();
        em.refresh(county);
        em.refresh(city);

        // county checks
        assertEquals(1, county.getCities().size());
        assertTrue(county.getCities().contains(city));

        // city check
        assertEquals("Milton Keynes", city.toString());
        assertEquals("Buckinghamshire", city.getCounty().toString());
        assertEquals("England", city.getCounty().getCountry().toString());

        logger.info("DEBUG");
    }
}