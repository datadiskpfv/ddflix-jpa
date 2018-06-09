package uk.co.datadisk.ddflixjpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.co.datadisk.ddflixjpa.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
