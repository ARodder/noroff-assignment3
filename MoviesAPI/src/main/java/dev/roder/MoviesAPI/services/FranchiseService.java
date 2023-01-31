package dev.roder.MoviesAPI.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.roder.MoviesAPI.entities.Franchise;
import dev.roder.MoviesAPI.repositories.FranchiseRepository;

@Service
public class FranchiseService {

    @Autowired
    private FranchiseRepository franchiseRepository;
    

    public Franchise findById(int id){
        //TODO: Add custom exception
        return franchiseRepository.findById(id).orElseThrow();
    }
    
}
