package dev.roder.MoviesAPI.services.movie;

import java.util.Collection;

import org.springframework.stereotype.Service;

import dev.roder.MoviesAPI.entities.Movie;
import dev.roder.MoviesAPI.entities.MovieCharacter;
import dev.roder.MoviesAPI.exceptions.MovieNotFoundException;
import dev.roder.MoviesAPI.repositories.CharacterRepository;
import dev.roder.MoviesAPI.repositories.MovieRepository;
import dev.roder.MoviesAPI.services.CrudService;

/**
 * Takes care of the logic behind the different operations available in the endpoints 
 */

@Service
public class MovieService implements CrudService<Movie,Integer>{

    private MovieRepository movieRepository;
    private CharacterRepository characterRepository;


    public MovieService(MovieRepository movieRepository,CharacterRepository characterRepository) {
        this.movieRepository = movieRepository;
        this.characterRepository = characterRepository;
    }


    /**
     * Adds a new movie to the database
     * @param newMovie new movie to add to the database.
     * @return returns the id of the new movie.
     */
    public Movie add(Movie newMovie){
        return movieRepository.save(newMovie);
    }

    /**
     * Retrieves a Collection of all the movies available in the database.
     * @return Returns a list of all the movies in the database.
     */
    public Collection<Movie> findAll(){
        return movieRepository.findAll();
    }

    /**
     * Retrieves a movie based on a given id.
     * @param id id of the movie to find
     * @return returns the found movie, or throws an exception.
     */
    public Movie findById(Integer id){
        return movieRepository.findById(id).orElseThrow(()-> new MovieNotFoundException(id));
    }
  
    /**
     * Updates a movie with the new values
     * @param entity new values of the movie
     */
    public Movie update(Movie entity){
        return movieRepository.save(entity);
    }

    /**
     * Deletes a movie based on the id.
     * @param id id of the movie to delete.
     * @return returns the deleted movie.
     */
    public void delete(Integer id){
        Movie movie = findById(id);
        movie.setFranchise(null);
        for(MovieCharacter character: movie.getCharacters()){
            character.removeMovie(movie);
            characterRepository.save(character);
        }
        movieRepository.delete(movie);
    }


    public boolean exists(Integer id){
        return movieRepository.existsById(id);
    }
}
