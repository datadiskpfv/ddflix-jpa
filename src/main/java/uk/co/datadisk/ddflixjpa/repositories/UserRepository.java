package uk.co.datadisk.ddflixjpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import uk.co.datadisk.ddflixjpa.entities.User;

import java.util.Collection;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    @Modifying  // this will effectively drop all non-flushed changes still pending in the EntityManager
    void deleteUsersByIdIn(Collection<Long> userIds);
}