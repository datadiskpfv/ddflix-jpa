package uk.co.datadisk.ddflixjpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.co.datadisk.ddflixjpa.entities.ShippingAddress;

public interface ShippingAddressRepository extends JpaRepository<ShippingAddress, Long> {
}