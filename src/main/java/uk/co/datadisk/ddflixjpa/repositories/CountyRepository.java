package uk.co.datadisk.ddflixjpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.co.datadisk.ddflixjpa.entities.County;

public interface CountyRepository extends JpaRepository<County, Long> {

    County findByCounty(String county);
}
