package dev.roder.MoviesAPI.services.movie;

import java.util.List;
import java.util.Collection;

import dev.roder.MoviesAPI.entities.Movie;
import dev.roder.MoviesAPI.entities.MovieCharacter;
import dev.roder.MoviesAPI.services.CrudService;
public interface MovieService extends CrudService<Movie, Integer>{
   public void updateCharactersInMovie(Integer id, List<Integer> characterIds);
   public Collection<MovieCharacter> findAllCharactersInMovie(Integer id);
}
