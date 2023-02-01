package dev.roder.MoviesAPI.controllers;

import dev.roder.MoviesAPI.entities.DTOs.character.MovieCharacterDTO;
import dev.roder.MoviesAPI.entities.DTOs.character.MovieCharacterPostDTO;
import dev.roder.MoviesAPI.entities.DTOs.character.MovieCharacterUpdateDTO;
import dev.roder.MoviesAPI.mappers.CharacterMapper;
import dev.roder.MoviesAPI.services.character.CharacterService;
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

@RestController
@RequestMapping(path = "api/v1/character")
public class CharacterController {

    private final CharacterService characterService;
    private final CharacterMapper characterMapper;

    public CharacterController(CharacterService characterService, CharacterMapper characterMapper) {
        this.characterService = characterService;
        this.characterMapper = characterMapper;
    }

    @GetMapping("{id}")
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

    @GetMapping
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

    @PostMapping
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

    @PutMapping("{id}")
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

    @DeleteMapping("{id}")
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

    @RequestMapping(value = "exists/{id}", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Success",
                    content = @Content
            )
    })
    public ResponseEntity exists(@PathVariable int id){
        return ResponseEntity.ok(characterService.exists(id));
    }
}
