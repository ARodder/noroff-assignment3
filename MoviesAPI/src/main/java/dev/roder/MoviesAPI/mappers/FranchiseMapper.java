package dev.roder.MoviesAPI.mappers;

import dev.roder.MoviesAPI.entities.Franchise;
import dev.roder.MoviesAPI.entities.Movie;
import dev.roder.MoviesAPI.entities.DTOs.franchise.FranchiseDTO;
import dev.roder.MoviesAPI.entities.DTOs.franchise.FranchisePostDTO;
import dev.roder.MoviesAPI.services.movie.MovieService;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class FranchiseMapper {

    @Autowired
    private MovieService movieService;

    @Mapping(target = "movies",qualifiedByName = "moviesToMovieIds")
    public abstract FranchiseDTO franchiseToFranchiseDTO(Franchise franchise);

    public abstract Franchise franchisePostDTOToFranchise(FranchisePostDTO franchisePostDTO);

    @Mapping(target = "movies", qualifiedByName = "movieIdsToMovie")
    public abstract Franchise franchiseDTOToFranchise(FranchiseDTO franchiseDTO);
    public abstract Collection<FranchiseDTO> franchiseToFranchiseDTO(Collection<Franchise> franchise);

    @Named(value="moviesToMovieIds")
    public Set<Integer> moviesToMovieId(Set<Movie> movies){
        return movies.stream().map((movie)->movie.getId()).collect(Collectors.toSet());
    }

    @Named(value="movieIdsToMovie")
    public Set<Movie> movieIdsToMovie(Set<Integer> moviesIds){
        return moviesIds.stream().map((movieId)->movieService.findById(movieId)).collect(Collectors.toSet());
    }
}
