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
import uk.co.datadisk.ddflixjpa.entities.ShippingAddress;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DdflixJpaApplication.class)
@TestPropertySource("classpath:application-test.properties")
public class ShippingAddressRepositoryTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    EntityManager em;

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    CountyRepository countyRepository;

    @Autowired
    ShippingAddressRepository shippingAddressRepository;

    @Before
    public void setUp() {
        ShippingAddress shippingAddress = new ShippingAddress();
        shippingAddress.setShippingAddressNumber("1");
        shippingAddress.setShippingAddressName("The Willows");
        shippingAddress.setShippingAddressStreet1("River Lane");
        shippingAddress.setShippingAddressStreet2("Lake Estate");
        shippingAddress.setShippingAddressPostcode("RL11 2RL");
        shippingAddress.setDefaultAddress(true);
        shippingAddressRepository.save(shippingAddress);
    }

    @Test
    @Transactional
    public void readCounty(){
        // the create happens in the setUp method - see @Before
        assertEquals("The Willows", shippingAddressRepository.findByShippingAddressName("The Willows").getShippingAddressName());
        assertEquals("The Willows", shippingAddressRepository.findByShippingAddressNumberAndShippingAddressStreet1("1", "River Lane").getShippingAddressName());
    }
}