package uk.co.datadisk.ddflixjpa.repositories.film;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.co.datadisk.ddflixjpa.entities.film.Film;

public interface FilmRepository extends JpaRepository<Film, Long> {

}
