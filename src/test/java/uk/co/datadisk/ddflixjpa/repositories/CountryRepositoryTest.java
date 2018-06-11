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
import uk.co.datadisk.ddflixjpa.entities.Country;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DdflixJpaApplication.class)
@TestPropertySource("classpath:application-test.properties")
public class CountryRepositoryTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CountryRepository countryRepository;

    @Before
    public void setUp() throws Exception {

        // the setUp actually tests the save function
        Country country = new Country();
        country.setCountry("England");
        countryRepository.save(country);
    }

    @Test
    @Transactional
    public void readCountry(){
        // the create happens in the setUp method - see @Before
        assertEquals("England", countryRepository.findByCountry("England").getCountry());
    }


    @Test
    @Transactional
    public void deleteCountry(){
        Country country = countryRepository.findByCountry("England");
        assertEquals("England", country.getCountry());

        countryRepository.delete(country);
        assertNull(countryRepository.findByCountry("England"));
    }

    @Test
    @Transactional
    public void updateCountry(){
        Country country = countryRepository.findByCountry("England");
        assertEquals("England", country.getCountry());

        country.setCountry("England - updated");
        countryRepository.save(country);
        assertEquals("England - updated", countryRepository.findByCountry("England - updated").getCountry());
    }

}