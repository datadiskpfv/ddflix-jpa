package uk.co.datadisk.ddflixjpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.co.datadisk.ddflixjpa.entities.ShippingAddress;

public interface ShippingAddressRepository extends JpaRepository<ShippingAddress, Long> {

    ShippingAddress findByShippingAddressName(String name);
    ShippingAddress findByShippingAddressNumberAndShippingAddressStreet1(String number, String street1);
}