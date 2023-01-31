package dev.roder.MoviesAPI.exceptions;


import jakarta.persistence.EntityNotFoundException;

public class FranchiseNotFoundException extends EntityNotFoundException{

    public FranchiseNotFoundException(int id) {
        super("Movie does not exist with ID: " + id);
    }
    
}