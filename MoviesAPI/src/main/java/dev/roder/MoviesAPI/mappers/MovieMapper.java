package dev.roder.MoviesAPI.mappers;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dev.roder.MoviesAPI.entities.Franchise;
import dev.roder.MoviesAPI.entities.Movie;
import dev.roder.MoviesAPI.entities.MovieCharacter;
import dev.roder.MoviesAPI.entities.DTOs.movie.MovieDTO;
import dev.roder.MoviesAPI.entities.DTOs.movie.MoviePostDTO;
import dev.roder.MoviesAPI.services.CharacterService;
import dev.roder.MoviesAPI.services.FranchiseService;

@Mapper(componentModel = "spring")
public abstract class MovieMapper {

    @Autowired
    private CharacterService characterService;

    @Autowired
    private FranchiseService franchiseService;

    @Mapping(target = "franchise", source = "franchise.id")
    @Mapping(target = "characters", qualifiedByName = "characterToCharacterId")
    public abstract MovieDTO movieToMovieDTO(Movie movie);

    public abstract Movie moviePostDTOToMovie(MoviePostDTO moviePostDto);

    public abstract Collection<MovieDTO> movieToMovieDTO(Collection<Movie> movie);

    @Mapping(target = "franchise", qualifiedByName = "franchiseIdToFranchise")
    @Mapping(target = "characters", qualifiedByName = "characterIdToCharacter")
    public abstract Movie movieDTOToMovie(MovieDTO movie);

    @Named(value = "characterToCharacterId")
    public Set<Integer> map(Set<MovieCharacter> value) {
        if (value == null) {
            return null;
        }
        return value.stream().map(c -> c.getId()).collect(Collectors.toSet());
    }

    @Named(value = "characterIdToCharacter")
    public Set<MovieCharacter> mapIdToCharacter(Set<Integer> value) {
        if (value == null) {
            return null;
        }
        return value.stream().map(c -> characterService.findById(c)).collect(Collectors.toSet());
    }

    @Named(value = "franchiseIdToFranchise")
    public Franchise mapIdToFranchise(Integer id){
        if(id==null){
            return null;
        }
        return franchiseService.findById(id);
    }

}
