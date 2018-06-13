package uk.co.datadisk.ddflixjpa.repositories.film;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import uk.co.datadisk.ddflixjpa.DdflixJpaApplication;
import uk.co.datadisk.ddflixjpa.entities.film.Film;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DdflixJpaApplication.class)
@TestPropertySource("classpath:application-test.properties")
public class FilmRepositoryTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    FilmRepository filmRepository;

    @Before
    public void setUp() {

        Film film = new Film("Alien");
        filmRepository.save(film);
    }

    @Test
    @Transactional
    @Rollback(false)
    public void deleteSpecifiedFilms() {

        filmRepository.save(new Film("Aliens"));
        filmRepository.save(new Film("Safe"));
        filmRepository.save(new Film("Ask A Policeman"));

        assertEquals(4, filmRepository.findAll().size());

        Collection<Long> filmIds = new ArrayList<>();
        filmIds.add(2L);
        filmIds.add(3L);

        filmRepository.deleteFilmsByIdIn(filmIds);
        assertEquals(2, filmRepository.findAll().size());

    }
}