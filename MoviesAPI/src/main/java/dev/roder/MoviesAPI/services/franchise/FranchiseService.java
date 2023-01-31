package dev.roder.MoviesAPI.services.franchise;

import java.util.List;

import dev.roder.MoviesAPI.entities.Franchise;
import dev.roder.MoviesAPI.services.CrudService;

public interface FranchiseService extends CrudService<Franchise, Integer> {
    Franchise updateMoviesInFranchise(Integer id, List<Integer> movieIds);
}
