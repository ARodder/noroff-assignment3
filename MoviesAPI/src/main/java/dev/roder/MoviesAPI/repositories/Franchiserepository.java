package dev.roder.MoviesAPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.roder.MoviesAPI.entities.Franchise;

@Repository
public interface Franchiserepository extends JpaRepository<Franchise,Integer>{
    
}
