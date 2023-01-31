package dev.roder.MoviesAPI.services;

import java.util.Collection;

import org.springframework.stereotype.Service;

import dev.roder.MoviesAPI.entities.Movie;
import dev.roder.MoviesAPI.exceptions.MovieNotFoundException;
import dev.roder.MoviesAPI.repositories.MovieRepository;

@Service
public class MovieService {

    private MovieRepository movieRepository;


    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }


    public Integer add(Movie newMovie){
        
        movieRepository.save(newMovie);


        return newMovie.getId();
    }

    public Collection<Movie> findAll(){
        return movieRepository.findAll();
    }

    public Movie findById(int id){
        return movieRepository.findById(id).orElseThrow(()-> new MovieNotFoundException(id));
    }
    
    public void update(Movie entity){

    }
}
