package dev.roder.MoviesAPI.controllers;

import dev.roder.MoviesAPI.entities.DTOs.franchise.FranchiseDTO;
import dev.roder.MoviesAPI.entities.DTOs.franchise.FranchisePostDTO;
import dev.roder.MoviesAPI.entities.DTOs.movie.MovieDTO;
import dev.roder.MoviesAPI.exceptions.FranchiseNotFoundException;
import dev.roder.MoviesAPI.mappers.CharacterMapper;
import dev.roder.MoviesAPI.mappers.FranchiseMapper;
import dev.roder.MoviesAPI.mappers.MovieMapper;
import dev.roder.MoviesAPI.services.franchise.FranchiseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/franchise")
public class FranchiseController {

        private final FranchiseService franchiseService;
        private final FranchiseMapper franchiseMapper;
        private final MovieMapper movieMapper;
        private final CharacterMapper characterMapper;

        public FranchiseController(FranchiseService franchiseService, FranchiseMapper franchiseMapper,
                        MovieMapper movieMapper, CharacterMapper characterMapper) {
                this.franchiseService = franchiseService;
                this.franchiseMapper = franchiseMapper;
                this.movieMapper = movieMapper;
                this.characterMapper = characterMapper;
        }

        @GetMapping
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Success", content = {
                                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = FranchiseDTO.class)))
                        })
        })
        public ResponseEntity<Collection<FranchiseDTO>> findAll() {
                return ResponseEntity.ok(franchiseMapper.franchiseToFranchiseDTO(
                                franchiseService.findAll()));
        }

        @GetMapping("{id}")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Success", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = FranchiseDTO.class))
                        }),
                        @ApiResponse(responseCode = "400", description = "Bad request", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))
                        }),
                        @ApiResponse(responseCode = "404", description = "Element with the provided ID does not exist", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))
                        })
        })
        public ResponseEntity<FranchiseDTO> findById(@PathVariable int id) {
                return ResponseEntity.ok(franchiseMapper.franchiseToFranchiseDTO(franchiseService.findById(id)));
        }

        @PostMapping
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Created", content = @Content)
        })
        public ResponseEntity add(@RequestBody FranchisePostDTO entity) {
                franchiseService.add(franchiseMapper.franchisePostDTOToFranchise(entity));
                return ResponseEntity.created(URI.create("api/v1/franchise/?")).build();
        }

        @PutMapping("{id}")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "204", description = "Updated successfully", content = @Content),
                        @ApiResponse(responseCode = "400", description = "Bad request", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))
                        }),
                        @ApiResponse(responseCode = "404", description = "Element with the provided ID does not exist", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))
                        })
        })
        public ResponseEntity update(@RequestBody FranchiseDTO entity, @PathVariable int id) {
                if (id != entity.getId()) {
                        return ResponseEntity.badRequest().build();
                }
                franchiseService.update(franchiseMapper.franchiseDTOToFranchise(entity));
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        @DeleteMapping("{id}")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "204", description = "Deleted successfully", content = @Content),
                        @ApiResponse(responseCode = "400", description = "Bad request", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))
                        }),
                        @ApiResponse(responseCode = "404", description = "Element with the provided ID does not exist", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))
                        })
        })
        public ResponseEntity delete(@RequestBody FranchiseDTO entity, @PathVariable int id) {
                if (id != entity.getId()) {
                        return ResponseEntity.badRequest().build();
                }
                franchiseService.delete(id);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        @RequestMapping(value = "exists/{id}", method = RequestMethod.GET)
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Success", content = @Content)
        })
        public ResponseEntity exists(@PathVariable int id) {
                return ResponseEntity.ok(franchiseService.exists(id));
        }

        /**
         * Creates an endpoint to update movies franchises based only on the id of the
         * franchise and the id of the movies to update.
         * 
         * @param id       id of the franchise to set the movies to.
         * @param movieIds ids of the movies to set the franchise to
         * @return returns the updated franchise.
         */
        @PutMapping("{id}/updateMovies")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Deleted successfully", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = FranchiseDTO.class)))),
                        @ApiResponse(responseCode = "404", description = "Element with the provided ID does not exist", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))
                        })
        })
        public ResponseEntity updateMoviesInFranchise(@PathVariable Integer id, @RequestBody List<Integer> movieIds) {
                try {

                        return ResponseEntity.ok(franchiseMapper
                                        .franchiseToFranchiseDTO(
                                                        franchiseService.updateMoviesInFranchise(id, movieIds)));
                } catch (FranchiseNotFoundException e) {
                        return ResponseEntity.notFound().build();
                }
        }

        /**
         * Retrieves all the movies in a franchise.
         * 
         * @param id if of the franchise to retrieve movies from.
         * @return collection of the found movies
         */
        @GetMapping("{id}/movies")
        @Operation(summary = "Gets all the movies in a franchise")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Success", content = {
                                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = MovieDTO.class)))
                        }),
                        @ApiResponse(responseCode = "404", description = "Element with the provided ID does not exist", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))
                        })
        })
        public ResponseEntity getAllMoviesInFranchise(@PathVariable Integer id) {
                try {
                        return ResponseEntity
                                        .ok(movieMapper.movieToMovieDTO(franchiseService.getAllMoviesInFranchise(id)));
                } catch (FranchiseNotFoundException e) {
                        return ResponseEntity.notFound().build();
                }
        }

        /**
         * Retrieves all the characters from a franchise based on a given id
         * @param id id of the franchise to retrieve characters from.
         * @return collection of all the characters in the franchise.
         */
        @GetMapping("{id}/characters")
        @Operation(summary = "Gets all the characters in a franchise")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Success", content = {
                                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = MovieDTO.class)))
                        }),
                        @ApiResponse(responseCode = "404", description = "Element with the provided ID does not exist", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))
                        })
        })
        public ResponseEntity getAllCharactersInFranchise(@PathVariable Integer id) {
                try {
                        return ResponseEntity.ok(characterMapper.movieCharacterToMovieCharacterDTO(
                                        franchiseService.getAllCharactersInFranchise(id)));
                } catch (FranchiseNotFoundException e) {
                        return ResponseEntity.notFound().build();
                } catch (Exception e) {
                        return ResponseEntity.badRequest().build();
                }
        }
}
