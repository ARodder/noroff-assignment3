package dev.roder.MoviesAPI.services.character;

import dev.roder.MoviesAPI.entities.MovieCharacter;
import dev.roder.MoviesAPI.exceptions.CharacterNotFoundException;
import dev.roder.MoviesAPI.repositories.CharacterRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * Service acting as the middleman in communication between
 * REST endpoints and the repository that queries the database
 * Any additional business logic needed to handle requests is found here
 * in order to relieve responsibilities of the REST controllers
 */
@Service
public class CharacterServiceImpl implements CharacterService {

    private final CharacterRepository characterRepository;

    public CharacterServiceImpl(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    /**
     * Retrieves a specific Character entity
     * @param integer: Integer identifier representing the unique Character entity
     * @return Character entity object
     */
    @Override
    public MovieCharacter findById(Integer integer) { return characterRepository.findById(integer).orElseThrow(
            () -> new CharacterNotFoundException(integer)
    ); }

    /**
     * Retrieves all Character entities
     * @return A Collection of Character entities
     */
    @Override
    public Collection<MovieCharacter> findAll() { return characterRepository.findAll(); }

    /**
     * Adds a new Character entity to the database
     * @param entity: Character entity to be added
     * @return The Character entity that was added
     */
    @Override
    public MovieCharacter add(MovieCharacter entity) { return characterRepository.save(entity); }

    /**
     * Updates an existing Character entity with new values
     * @param entity: Character entity to be updated
     * @return Character entity that was updated
     */
    @Override
    public MovieCharacter update(MovieCharacter entity) {
        if(exists(entity.getId())){
            MovieCharacter existingCharacter = characterRepository.findById(entity.getId()).orElseThrow(()-> new CharacterNotFoundException(entity.getId()));

            if(entity.getMovies() != null){
                existingCharacter.setMovies(entity.getMovies());
            }
            if(entity.getName() != null){
                existingCharacter.setName(entity.getName());
            }
            if(entity.getAlias() != null){
                existingCharacter.setAlias(entity.getAlias());
            }
            if(entity.getGender() != null){
                existingCharacter.setGender(entity.getGender());
            }
            if(entity.getUrl() != null){
                existingCharacter.setUrl(entity.getUrl());
            }

            return characterRepository.save(existingCharacter);
        }else{
            throw new CharacterNotFoundException(entity.getId());
        }
    }

    /**
     * Deletes a Character entity object from the database
     * Sets all foreign keys of entities relating to the Character to null
     * It is annotated with Transactional because the connection to the database
     * needs to stay open in order to complete more than one repository request
     * @param integer: Integer identifier representing the Character entity object to be deleted
     */
    @Override
    @Transactional
    public void delete(Integer integer) {
        // We first set the foreign keys to null in order to be able
        // to delete the entity without violating foreign key constraints
        characterRepository.updateMovieCharacterDeleteRelatedConnections(integer);
        // Now we can safely delete the entity in question
        characterRepository.deleteById(integer);
    }

    /**
     * Checks if a specific Character entity exists
     * @param integer: Integer identifier representing the Character entity
     * @return Boolean value representing the existence of the entity
     *         True: Entity exists, False; Entity does not exist
     */
    @Override
    public boolean exists(Integer integer) { return characterRepository.existsById(integer); }
}
