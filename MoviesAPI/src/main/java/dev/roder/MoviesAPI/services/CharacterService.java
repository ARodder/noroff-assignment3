package dev.roder.MoviesAPI.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.roder.MoviesAPI.entities.MovieCharacter;
import dev.roder.MoviesAPI.repositories.CharacterRepository;

@Service
public class CharacterService {
    @Autowired
    private CharacterRepository characterRepository;
    

    public MovieCharacter findById(int id){
        return characterRepository.findById(id).orElseThrow();
    }
    
}
