package uk.co.datadisk.ddflixjpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.co.datadisk.ddflixjpa.entities.Country;

public interface CountryRepository extends JpaRepository<Country, Long> {

    Country findByCountry(String country);
}
