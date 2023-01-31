package dev.roder.MoviesAPI.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class CharacterNotFoundException extends EntityNotFoundException {

    public CharacterNotFoundException(int id) {
        super("Character does not exist with ID: " + id);
    }

}
