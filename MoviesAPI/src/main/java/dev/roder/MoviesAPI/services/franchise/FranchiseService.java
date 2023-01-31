package dev.roder.MoviesAPI.services.franchise;

import java.util.List;
import java.util.Collection;

import dev.roder.MoviesAPI.entities.Franchise;
import dev.roder.MoviesAPI.entities.Movie;
import dev.roder.MoviesAPI.entities.MovieCharacter;
import dev.roder.MoviesAPI.services.CrudService;

public interface FranchiseService extends CrudService<Franchise, Integer> {
    Franchise updateMoviesInFranchise(Integer id, List<Integer> movieIds);
    Collection<Movie> getAllMoviesInFranchise(Integer id);
    Collection<MovieCharacter> getAllCharactersInFranchise(Integer id);
}
