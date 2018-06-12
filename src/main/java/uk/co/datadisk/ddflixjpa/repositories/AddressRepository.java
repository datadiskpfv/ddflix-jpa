package uk.co.datadisk.ddflixjpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.co.datadisk.ddflixjpa.entities.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

    Address findByName(String name);
    Address findByNumberAndStreet1(String number, String street1);
}