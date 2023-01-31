package dev.roder.MoviesAPI.services.franchise;

import dev.roder.MoviesAPI.entities.Franchise;
import dev.roder.MoviesAPI.repositories.FranchiseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
public class FranchiseServiceImpl implements FranchiseService {

    private final FranchiseRepository franchiseRepository;

    public FranchiseServiceImpl(FranchiseRepository franchiseRepository) {
        this.franchiseRepository = franchiseRepository;
    }

    @Override
    public Franchise findById(Integer integer) {
        return franchiseRepository.findById(integer).orElseThrow();
    }

    @Override
    public Collection<Franchise> findAll() {
        return franchiseRepository.findAll();
    }

    @Override
    public Franchise add(Franchise entity) {
        return franchiseRepository.save(entity);
    }

    @Override
    public Franchise update(Franchise entity) {
        return franchiseRepository.save(entity);
    }

    @Override
    @Transactional
    public void delete(Integer integer) {
        franchiseRepository.updateForeignKeyMovieSetNull(integer);
        franchiseRepository.deleteById(integer);
    }

    @Override
    public boolean exists(Integer integer) {
        return franchiseRepository.existsById(integer);
    }
}
