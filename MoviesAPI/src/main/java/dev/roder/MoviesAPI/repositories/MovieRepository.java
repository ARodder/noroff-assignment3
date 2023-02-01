package dev.roder.MoviesAPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dev.roder.MoviesAPI.entities.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie,Integer>{

    /**
     * Extension method for MovieRepository that creates a new
     * relation/link between a movie and a character by inserting this relation
     * in the linking table movie_characters, effectively "adding" a character
     * to a movie in the domain sense.
     * The "ON CONFLICT DO NOTHING" prevents an SQLException when attempting to
     * insert a new relation that already exists, and relieves responsibility of correctness
     * of input from Client.
     * @param movie_id: Integer identifier of specific Movie entity
     * @param char_id: Integer identifier of specific Character entity
     */
    @Modifying
    @Query(value = "INSERT INTO movie_characters(movie_id, character_id) VALUES (?1, ?2) ON CONFLICT DO NOTHING", nativeQuery = true)
    void addCharacterMovieRelation(@Param("movie_id") Integer movie_id, @Param("char_id") Integer char_id);
}