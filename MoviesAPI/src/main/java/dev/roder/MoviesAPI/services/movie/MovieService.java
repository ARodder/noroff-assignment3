package dev.roder.MoviesAPI.services.movie;

import java.util.Collection;
import java.util.List;

import dev.roder.MoviesAPI.exceptions.CharacterNotFoundException;
import org.springframework.stereotype.Service;

import dev.roder.MoviesAPI.entities.Movie;
import dev.roder.MoviesAPI.entities.MovieCharacter;
import dev.roder.MoviesAPI.exceptions.MovieNotFoundException;
import dev.roder.MoviesAPI.repositories.CharacterRepository;
import dev.roder.MoviesAPI.repositories.MovieRepository;
import dev.roder.MoviesAPI.services.CrudService;
import org.springframework.transaction.annotation.Transactional;

/**
 * Takes care of the logic behind the different operations available in the endpoints 
 */

@Service
public interface MovieService extends CrudService<Movie, Integer> {
    public void updateCharactersInMovie(Integer id, List<Integer> characterIds);
    public Collection<MovieCharacter> findAllCharactersInMovie(Integer id);
}