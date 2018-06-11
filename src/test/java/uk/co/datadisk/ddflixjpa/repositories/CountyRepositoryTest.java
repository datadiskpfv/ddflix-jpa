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
import uk.co.datadisk.ddflixjpa.entities.Country;
import uk.co.datadisk.ddflixjpa.entities.County;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DdflixJpaApplication.class)
@TestPropertySource("classpath:application-test.properties")
public class CountyRepositoryTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    EntityManager em;

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    CountyRepository countyRepository;

    @Before
    public void setUp() throws Exception {

        // Create a new county
        County county = new County();
        county.setCounty("Buckinghamshire");
        countyRepository.save(county);
    }

    @Test
    @Transactional
    public void readCounty(){
        // the create happens in the setUp method - see @Before
        assertEquals("Buckinghamshire", countyRepository.findByCounty("Buckinghamshire").getCounty());
    }

    @Test
    @Transactional
    public void deleteCounty(){
        County county = countyRepository.findByCounty("Buckinghamshire");
        assertEquals("Buckinghamshire", county.getCounty());

        countyRepository.delete(county);
        assertNull(countyRepository.findByCounty("Buckinghamshire"));
    }

    @Test
    @Transactional
    public void updateCounty(){
        County county = countyRepository.findByCounty("Buckinghamshire");
        assertEquals("Buckinghamshire", county.getCounty());

        county.setCounty("Buckinghamshire - updated");
        countyRepository.save(county);
        assertEquals("Buckinghamshire - updated", countyRepository.findByCounty("Buckinghamshire - updated").getCounty());
    }

    @Test
    @Transactional
    //@Rollback(false)
    public void addCountyToCountry() {

        // the setUp actually tests the save function
        Country country = new Country();
        country.setCountry("England");
        countryRepository.save(country);

        County county = countyRepository.findByCounty("Buckinghamshire");
        county.setCountry(country);
        countyRepository.save(county);

        // flush changes to the database and refresh the country entity to pick the changes
        // the commit does not happen until after the test, hence why we do below
        em.flush();
        em.refresh(country);
        em.refresh(county);

        // country checks
        assertEquals(1, country.getCounties().size());
        assertTrue(country.getCounties().contains(county));

        // county checks
        assertEquals("England", county.getCountry().toString());
    }
}