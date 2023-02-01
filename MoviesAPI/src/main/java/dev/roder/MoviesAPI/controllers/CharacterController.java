package dev.roder.MoviesAPI.controllers;

import dev.roder.MoviesAPI.entities.DTOs.character.MovieCharacterDTO;
import dev.roder.MoviesAPI.entities.DTOs.character.MovieCharacterPostDTO;
import dev.roder.MoviesAPI.entities.DTOs.character.MovieCharacterUpdateDTO;
import dev.roder.MoviesAPI.mappers.CharacterMapper;
import dev.roder.MoviesAPI.services.character.CharacterService;
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

/**
 * REST Controller that acts as the outermost
 * API for communication with clients using URL-based
 * endpoint communication that passes requests on to services
 */
@RestController
@RequestMapping(path = "api/v1/character")
public class CharacterController {

    private final CharacterService characterService;
    private final CharacterMapper characterMapper;

    public CharacterController(CharacterService characterService, CharacterMapper characterMapper) {
        this.characterService = characterService;
        this.characterMapper = characterMapper;
    }

    /**
     * Retrieves a specific Character
     * @param id: Integer identifier representing the unique Character to retrieve
     * @return A Data Transfer Object representing the Character retrieved
     */
    @GetMapping("{id}")
    @Operation(summary = "Retrieves a Character by their ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Success",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = MovieCharacterDTO.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ProblemDetail.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Element with the provided ID does not exist",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ProblemDetail.class))
                    }
            )
    })
    public ResponseEntity findById(@PathVariable int id){
        return ResponseEntity.ok(
                characterMapper.movieCharacterToMovieCharacterDTO(
                        characterService.findById(id)
                ));
    }

    /**
     * Retrieves all Characters
     * @return A Collection of Data Transfer Objects representing the retrieved Characters
     */
    @GetMapping
    @Operation(summary = "Retrieves all Characters")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Success",
                    content = {
                            @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = MovieCharacterDTO.class)))
                    }
            )
    })
    public ResponseEntity findAll(){
        return ResponseEntity.ok(
                characterMapper.movieCharacterToMovieCharacterDTO(
                   characterService.findAll()
                ));
    }

    /**
     * Adds a new entry of a Character in the database
     * @param entity: A Data Transfer Object representing the Character entity to be added
     * @return The Universal Resource Identifier of the newly created resource
     */
    @PostMapping
    @Operation(summary = "Adds a new Character")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Created",
                    content = @Content
            )
    })
    public ResponseEntity add(@RequestBody MovieCharacterPostDTO entity){
        characterService.add(
                characterMapper.movieCharacterPostDTOToMovieCharacter(entity));
        return ResponseEntity.created(URI.create("api/v1/character/?")).build();
    }

    /**
     * Updates an existing entry of a Character
     * @param entity: A Data Transfer Object holding the values to be updated
     * @param id: Integer identifier representing the unique Character entry to be updated
     * @return Returns nothing if updated successfully
     */
    @PutMapping("{id}")
    @Operation(summary = "Updates an existing Character by their ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Updated successfully",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ProblemDetail.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Element with the provided ID does not exist",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ProblemDetail.class))
                    }
            )
    })
    public ResponseEntity update(@RequestBody MovieCharacterUpdateDTO entity, @PathVariable int id){
        if(id != entity.getId()){return ResponseEntity.badRequest().build();}
        characterService.update(
                characterMapper.movieCharacterUpdateDTOToMovieCharacter(entity));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * Deletes a Character entry
     * @param entity: A Data Transfer Object representing the Character to be deleted
     * @param id: Integer identifier representing the unique Character to be deleted
     * @return Returns nothing if deleted successfully
     */
    @DeleteMapping("{id}")
    @Operation(summary = "Deletes a Character by their ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Deleted successfully",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ProblemDetail.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Element with the provided ID does not exist",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ProblemDetail.class))
                    }
            )
    })
    public ResponseEntity delete(@RequestBody MovieCharacterDTO entity, @PathVariable int id){
        if(id != entity.getId()){return ResponseEntity.badRequest().build();}
        characterService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
