package dev.roder.MoviesAPI.services.franchise;

import dev.roder.MoviesAPI.entities.Franchise;
import dev.roder.MoviesAPI.entities.Movie;
import dev.roder.MoviesAPI.entities.MovieCharacter;
import dev.roder.MoviesAPI.exceptions.FranchiseNotFoundException;
import dev.roder.MoviesAPI.repositories.FranchiseRepository;
import dev.roder.MoviesAPI.repositories.MovieRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service acting as the middleman in communication between
 * REST endpoints and the repository that queries the database
 * Any additional business logic needed to handle requests is found here
 * in order to relieve responsibilities of the REST controllers
 */
@Service
public class FranchiseServiceImpl implements FranchiseService {

    private final FranchiseRepository franchiseRepository;

    private final MovieRepository movieRepository;

    public FranchiseServiceImpl(FranchiseRepository franchiseRepository,MovieRepository movieRepository) {
        this.franchiseRepository = franchiseRepository;
        this.movieRepository = movieRepository;
    }

    /**
     * Retrieves a specific Franchise
     * @param integer: Integer identifier of unique Franchise
     * @return Franchise entity object
     */
    @Override
    public Franchise findById(Integer integer) {
        return franchiseRepository.findById(integer).orElseThrow(() -> new FranchiseNotFoundException(integer));
    }

    /**
     * Retrieves all Franchises
     * @return A Collection of Franchise entity objects
     */
    @Override
    public Collection<Franchise> findAll() {
        return franchiseRepository.findAll();
    }

    /**
     * Adds a new Franchise to the database
     * @param entity: Franchise entity object to be added
     * @return The newly created Franchise entity object
     */
    @Override
    public Franchise add(Franchise entity) {
        return franchiseRepository.save(entity);
    }

    /**
     * Updates an existing Franchise entity object in the database
     * @param entity: Franchise entity object to be updated
     * @return The Franchise entity object that was updated
     */
    @Override
    public Franchise update(Franchise entity) {
        return franchiseRepository.save(entity);
    }

    /**
     * Deletes a Franchise entity object from the database
     * Sets all foreign keys of entities relating to the Franchise to null
     * It is annotated with Transactional because the connection to the database
     * needs to stay open in order to complete more than one repository request
     * @param integer: Integer identifier representing the Franchise entity object to be deleted
     */
    @Override
    @Transactional
    public void delete(Integer integer) {
        // We first set the foreign keys to null in order to be able
        // to delete the entity without violating foreign key constraints
        franchiseRepository.updateForeignKeyMovieSetNull(integer);
        // Now we can safely delete the entity in question
        franchiseRepository.deleteById(integer);
    }

    /**
     * Checks if a specific Franchise entity exists
     * @param integer: Integer identifier representing the Franchise entity
     * @return Boolean value representing the existence of the entity
     *         True: Entity exists, False; Entity does not exist
     */
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

    /**
     * Retrieves all Movies that relate to specific Franchise entity
     * @param id: Integer identifier representing Franchise entity
     * @return A Collection of Movie entities that relate to the specific Franchise
     */
    @Override
    public Collection<Movie> getAllMoviesInFranchise(Integer id) {
        // TODO Auto-generated method stub
        return franchiseRepository.findById(id).orElseThrow(()-> new FranchiseNotFoundException(id)).getMovies();
    }

    /**
     * Retrieves all Character entities that relate to a specific Franchise entity
     * @param id: Integer identifier representing specific Franchise entity
     * @return A Collection of Character entities that relate to specific Franchise
     */
    @Override
    @Transactional
    public Collection<MovieCharacter> getAllCharactersInFranchise(Integer id) {
        // TODO Auto-generated method stub
        Set<MovieCharacter> franchiseCharacters = new HashSet<>();
        for (Movie movie : franchiseRepository.findById(id).orElseThrow(()-> new FranchiseNotFoundException(id)).getMovies()) {
            franchiseCharacters.addAll(movie.getCharacters());
        }
        return franchiseCharacters;
    } 
}
