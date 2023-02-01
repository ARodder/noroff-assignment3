package dev.roder.MoviesAPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dev.roder.MoviesAPI.entities.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie,Integer>{
    @Modifying
    @Query(value = "INSERT INTO movie_characters(movie_id, character_id) VALUES (?1, ?2) ON CONFLICT DO NOTHING", nativeQuery = true)
    void addCharacterMovieRelation(@Param("movie_id") Integer movie_id, @Param("char_id") Integer char_id);
}