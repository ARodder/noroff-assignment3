package dev.roder.MoviesAPI.controllers;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.roder.MoviesAPI.entities.DTOs.movie.MovieDTO;
import dev.roder.MoviesAPI.entities.DTOs.movie.MoviePostDTO;
import dev.roder.MoviesAPI.mappers.MovieMapper;
import dev.roder.MoviesAPI.services.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping(path = "api/v1/movies")
public class MovieController {
    private MovieService movieService;

    private MovieMapper movieMapper;

    public MovieController(MovieService movieService, MovieMapper movieMapper) {
        this.movieService = movieService;
        this.movieMapper = movieMapper;
    }

    @PostMapping
    @Operation(summary = "Add a new Movie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content)
    })
    public ResponseEntity add(@RequestBody MoviePostDTO movie) throws URISyntaxException {

        int newEntityId = movieService.add(movieMapper.moviePostDTOToMovie(movie));
        // TODO: change location from null
        return ResponseEntity.created(new URI("api/v1/movies/" + newEntityId)).build();
    }

    @GetMapping
    @Operation(summary = "Gets all the movies")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = MovieDTO.class)))
            })
    })
    public ResponseEntity findAll() {
        return ResponseEntity.ok(movieMapper.movieToMovieDTO(movieService.findAll()));
    }

    @GetMapping("{id}")
    @Operation(summary = "Gets a movie by the movies id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = MovieDTO.class))
            }),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ProblemDetail.class))
            )

    })
    public ResponseEntity findById(@PathVariable int id) {
        return ResponseEntity.ok(movieMapper.movieToMovieDTO(movieService.findById(id)));
    }


    @PutMapping("{id}")
    @Operation(description = "Updates a movie")
    @ApiResponses(value = {
        @ApiResponse(
                responseCode = "204",
                description = "Success",
                content = @Content
        ),
        @ApiResponse(
                responseCode = "400",
                description = "Bad Request",
                content = @Content
        ),
        @ApiResponse(
                responseCode = "404",
                description = "Not Found",
                content = @Content
        )
})
public ResponseEntity update(@RequestBody MovieDTO movie, @PathVariable int id){
    if(id != movie.getId()){
        return ResponseEntity.badRequest().build();
    }
    movieService.update(movieMapper.movieDTOToMovie(movie));

    return ResponseEntity.ok().build();
}


}
