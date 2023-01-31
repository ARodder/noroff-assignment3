package dev.roder.MoviesAPI.mappers;

import dev.roder.MoviesAPI.entities.DTOs.character.MovieCharacterDTO;
import dev.roder.MoviesAPI.entities.DTOs.character.MovieCharacterPostDTO;
import dev.roder.MoviesAPI.entities.Movie;
import dev.roder.MoviesAPI.entities.MovieCharacter;
import dev.roder.MoviesAPI.services.movie.MovieService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class CharacterMapper {

    @Autowired
    private MovieService movieService;

    protected CharacterMapper() {}

    @Mapping(target="movies", qualifiedByName="movieToMovieId")
    public abstract MovieCharacterDTO movieCharacterToMovieCharacterDTO(MovieCharacter movieCharacter);
    @Mapping(target="movies", qualifiedByName = "movieIdToMovie")
    public abstract MovieCharacter movieCharacterDTOToMovieCharacter(MovieCharacterDTO movieCharacterDTO);

    public abstract MovieCharacter movieCharacterPostDTOToMovieCharacter(MovieCharacterPostDTO movieCharacterPostDTO);

    public abstract Collection<MovieCharacterDTO> movieCharacterToMovieCharacterDTO(Collection<MovieCharacter> movieCharacter);

    @Named("movieToMovieId")
    public Set<Integer> map(Set<Movie> value) {
        return value == null ? null : value.stream().map((c) -> {
            return c.getId();
        }).collect(Collectors.toSet());
    }

    @Named("movieIdToMovie")
    public Set<Movie> mapIdToMovie(Set<Integer> value) {
        return value == null ? null : value.stream().map((c) -> {
            return (Movie)this.movieService.findById(c);
        }).collect(Collectors.toSet());
    }

}
