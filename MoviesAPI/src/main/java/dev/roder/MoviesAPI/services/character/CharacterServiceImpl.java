package dev.roder.MoviesAPI.services.character;

import dev.roder.MoviesAPI.entities.MovieCharacter;
import dev.roder.MoviesAPI.exceptions.CharacterNotFoundException;
import dev.roder.MoviesAPI.repositories.CharacterRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public class CharacterServiceImpl implements CharacterService {

    private final CharacterRepository characterRepository;

    public CharacterServiceImpl(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    @Override
    public MovieCharacter findById(Integer integer) { return characterRepository.findById(integer).orElseThrow(
            () -> new CharacterNotFoundException(integer)
    ); }

    @Override
    public Collection<MovieCharacter> findAll() { return characterRepository.findAll(); }

    @Override
    public MovieCharacter add(MovieCharacter entity) { return characterRepository.save(entity); }

    @Override
    public MovieCharacter update(MovieCharacter entity) { return characterRepository.save(entity); }

    @Override
    @Transactional
    public void delete(Integer integer) {
        characterRepository.updateMovieCharacterDeleteRelatedConnections(integer);
        characterRepository.deleteById(integer);
    }

    @Override
    public boolean exists(Integer integer) { return characterRepository.existsById(integer); }
}
