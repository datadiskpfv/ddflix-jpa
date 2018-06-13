package uk.co.datadisk.ddflixjpa.repositories.film;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import uk.co.datadisk.ddflixjpa.entities.film.Film;

import java.util.Collection;

public interface FilmRepository extends JpaRepository<Film, Long> {

    @Modifying // this will effectively drop all non-flushed changes still pending in the EntityManager
    void deleteFilmsByIdIn(Collection<Long> filmIds);
}