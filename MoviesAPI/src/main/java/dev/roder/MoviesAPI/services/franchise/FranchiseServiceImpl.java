package dev.roder.MoviesAPI.services.franchise;

import dev.roder.MoviesAPI.entities.Franchise;
import dev.roder.MoviesAPI.entities.Movie;
import dev.roder.MoviesAPI.exceptions.FranchiseNotFoundException;
import dev.roder.MoviesAPI.repositories.FranchiseRepository;
import dev.roder.MoviesAPI.repositories.MovieRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FranchiseServiceImpl implements FranchiseService {

    private final FranchiseRepository franchiseRepository;

    private final MovieRepository movieRepository;

    public FranchiseServiceImpl(FranchiseRepository franchiseRepository,MovieRepository movieRepository) {
        this.franchiseRepository = franchiseRepository;
        this.movieRepository = movieRepository;
    }

    @Override
    public Franchise findById(Integer integer) {
        return franchiseRepository.findById(integer).orElseThrow();
    }

    @Override
    public Collection<Franchise> findAll() {
        return franchiseRepository.findAll();
    }

    @Override
    public Franchise add(Franchise entity) {
        return franchiseRepository.save(entity);
    }

    @Override
    public Franchise update(Franchise entity) {
        return franchiseRepository.save(entity);
    }

    @Override
    @Transactional
    public void delete(Integer integer) {
        franchiseRepository.updateForeignKeyMovieSetNull(integer);
        franchiseRepository.deleteById(integer);
    }

    @Override
    public boolean exists(Integer integer) {
        return franchiseRepository.existsById(integer);
    }

    /**
     * Updates the movies in a franchise using the id of the franchise 
     * and a list of the ids of the movies to be in the franchise
     */
    @Override
    public Franchise updateMoviesInFranchise(Integer id, List<Integer> movieIds){
        Franchise franchise = franchiseRepository.findById(id).orElseThrow(()-> new FranchiseNotFoundException(id));
        List<Movie> movies = movieIds.stream().map((mId)->movieRepository.findById(mId).orElse(null)).filter((m)-> m != null).collect(Collectors.toList());
        for(Movie movie: movies){
            movie.setFranchise(franchise);
            movieRepository.save(movie);
        }
        franchise.setMovies(movies.stream().collect(Collectors.toSet()));
        franchiseRepository.save(franchise);

        return franchise;
    } 
}
