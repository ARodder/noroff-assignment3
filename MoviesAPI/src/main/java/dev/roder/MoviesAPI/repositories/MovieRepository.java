package dev.roder.MoviesAPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.roder.MoviesAPI.entities.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie,Integer>{
    
}