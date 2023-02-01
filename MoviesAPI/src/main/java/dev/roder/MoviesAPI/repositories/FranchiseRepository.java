package dev.roder.MoviesAPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dev.roder.MoviesAPI.entities.Franchise;

@Repository
public interface FranchiseRepository extends JpaRepository<Franchise,Integer>{

    /**
     * Extension method for FranchiseRepository which sets the foreign keys
     * of all Movies that are related to a Franchise to null, effectively making those
     * Movies no longer "apart" of that Franchise in the domain sense. This is done to enable
     * the safe deletion of a Franchise.
     * @param id: Integer identifier representing the Franchise entity that Movies should
     *            no longer be linked to
     */
    @Modifying
    @Query(value = "UPDATE movie SET franchise_id = null WHERE franchise_id = ?1", nativeQuery = true)
    void updateForeignKeyMovieSetNull(@Param("id") Integer id);
}