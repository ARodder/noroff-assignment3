package dev.roder.MoviesAPI.controllers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import dev.roder.MoviesAPI.entities.DTOs.movie.MovieUpdateDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.roder.MoviesAPI.entities.DTOs.movie.MovieDTO;
import dev.roder.MoviesAPI.entities.DTOs.movie.MoviePostDTO;
import dev.roder.MoviesAPI.exceptions.MovieNotFoundException;
import dev.roder.MoviesAPI.mappers.CharacterMapper;
import dev.roder.MoviesAPI.mappers.MovieMapper;
import dev.roder.MoviesAPI.services.movie.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * RESTController for movies. Declares end handles all endpoints relating
 * to CRUD-Operations on movieEntity.
 */
@RestController
@RequestMapping(path = "api/v1/movies")
public class MovieController {
    private MovieService movieService;

    private MovieMapper movieMapper;
    private CharacterMapper characterMapper;

    public MovieController(MovieService movieService, MovieMapper movieMapper, CharacterMapper characterMapper) {
        this.movieService = movieService;
        this.movieMapper = movieMapper;
        this.characterMapper = characterMapper;
    }

    /**
     * Adds a new movie to the database.
     * 
     * @param movie new movie to add.
     * @return location of the new movie.
     * @throws URISyntaxException exception thrown if the URI is incorrectly
     *                            formatted.
     */
    @PostMapping
    @Operation(summary = "Add a new Movie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content)
    })
    public ResponseEntity add(@RequestBody MoviePostDTO movie) throws URISyntaxException {

        int newEntityId = movieService.add(movieMapper.moviePostDTOToMovie(movie)).getId();
        // TODO: change location from null
        return ResponseEntity.created(new URI("api/v1/movies/" + newEntityId)).build();
    }

    /**
     * Finds all movies in the database.
     * 
     * @return all movies found in the database
     */
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

    /**
     * Retrieves a specific movie from the database based
     * on id
     * 
     * @param id id of the movie to find
     * @return Returns the found movie, if none then returns 404.
     */
    @GetMapping("{id}")
    @Operation(summary = "Gets a movie by the movies id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = MovieDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class)))

    })
    public ResponseEntity findById(@PathVariable int id) {
        try {
            return ResponseEntity.ok(movieMapper.movieToMovieDTO(movieService.findById(id)));
        } catch (MovieNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Updates a movie
     * 
     * @param movie new values of the movie to update.
     * @param id    id of the movie to update
     * @return Returns ResponseEntity based on the success of the update.
     */
    @PutMapping("{id}")
    @Operation(summary = "Updates a movie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Success", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    })
    public ResponseEntity update(@RequestBody MovieUpdateDTO movie, @PathVariable int id) {
        if (id != movie.getId()) {
            return ResponseEntity.badRequest().build();
        }
        try {

            movieService.update(movieMapper.movieUpdateDTOToMovie(movie));
        } catch (MovieNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * Deletes a movie for a given id.
     * 
     * @param id id of the movie to delete.
     */
    @DeleteMapping("{id}")
    @Operation(summary = "Deletes a movie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MovieDTO.class))

            ),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class)))
    })
    public ResponseEntity delete(@PathVariable int id) {

        try {
            movieService.delete(id);
            return ResponseEntity.ok().build();
        } catch (MovieNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

    }

    @PutMapping("{id}/updateCharacters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Updated successfully", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))
            }),
            @ApiResponse(responseCode = "404", description = "Element with the provided ID does not exist", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))
            })
    })
    public ResponseEntity addCharacters(@RequestBody List<Integer> characterIds, @PathVariable int id) {
        try {
            movieService.updateCharactersInMovie(id, characterIds);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (MovieNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (CharacterNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Retrieves all the characters for a specific movie.
     * 
     * @param id id of the movie to retrieve characters from.
     * @return returns a collection of characters.
     */
    @GetMapping("{id}/characters")
    @Operation(summary = "Get all characters in a movie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated successfully", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))
            }),
            @ApiResponse(responseCode = "404", description = "Element with the provided ID does not exist", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))
            })
    })
    public ResponseEntity findAllCharactersInMovie(@PathVariable Integer id) {
        try {
            return ResponseEntity
                    .ok(characterMapper.movieCharacterToMovieCharacterDTO(movieService.findAllCharactersInMovie(id)));
        } catch (MovieNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

    }

}
