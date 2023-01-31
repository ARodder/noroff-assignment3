package dev.roder.MoviesAPI.services.character;

import dev.roder.MoviesAPI.services.CrudService;
import org.springframework.stereotype.Service;
import dev.roder.MoviesAPI.entities.MovieCharacter;

@Service
public interface CharacterService extends CrudService<MovieCharacter, Integer> {
    
}
