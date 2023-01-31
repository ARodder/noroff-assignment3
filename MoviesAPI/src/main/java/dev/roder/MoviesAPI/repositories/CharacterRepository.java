package dev.roder.MoviesAPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.roder.MoviesAPI.entities.MovieCharacter;

@Repository
public interface CharacterRepository extends JpaRepository<MovieCharacter,Integer>{
    
}
