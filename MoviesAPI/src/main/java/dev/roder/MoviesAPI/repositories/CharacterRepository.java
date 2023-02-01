package dev.roder.MoviesAPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dev.roder.MoviesAPI.entities.MovieCharacter;

@Repository
public interface CharacterRepository extends JpaRepository<MovieCharacter,Integer>{

    /**
     * Extension method for CharacterRepository which deletes all links a
     * specific Character has to any Movies, effectively making that Character
     * no longer "apart" of any movies in the domain sense. This is done to enable
     * the safe deletion of a Character entity.
     * @param id: Integer identifier representing specific Character entity
     */
    @Modifying
    @Query(value = "DELETE from movie_characters WHERE character_id = ?1",
            nativeQuery = true)
    void updateMovieCharacterDeleteRelatedConnections(@Param("id") Integer id);
}
