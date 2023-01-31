package dev.roder.MoviesAPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dev.roder.MoviesAPI.entities.Franchise;

@Repository
public interface FranchiseRepository extends JpaRepository<Franchise,Integer>{

    @Modifying
    @Query(value = "UPDATE movie SET franchise_id = null WHERE franchise_id = ?1", nativeQuery = true)
    void updateForeignKeyMovieSetNull(@Param("id") Integer id);
}