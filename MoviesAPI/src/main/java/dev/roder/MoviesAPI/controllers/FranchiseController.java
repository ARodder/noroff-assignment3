package dev.roder.MoviesAPI.controllers;

import dev.roder.MoviesAPI.entities.dto.franchise.FranchiseDTO;
import dev.roder.MoviesAPI.entities.dto.franchise.FranchisePostDTO;
import dev.roder.MoviesAPI.mappers.FranchiseMapper;
import dev.roder.MoviesAPI.services.franchise.FranchiseService;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping(path = "api/v1/franchise")
public class FranchiseController {

    private final FranchiseService franchiseService;
    private final FranchiseMapper franchiseMapper;

    public FranchiseController(FranchiseService franchiseService, FranchiseMapper franchiseMapper) {
        this.franchiseService = franchiseService;
        this.franchiseMapper = franchiseMapper;
    }

    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Success",
                    content = {
                            @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = FranchiseDTO.class)))
                    }
            )
    })
    public ResponseEntity<Collection<FranchiseDTO>> findAll(){
        return ResponseEntity.ok(franchiseMapper.franchiseToFranchiseDTO(
                franchiseService.findAll()
        ));
    }

    @GetMapping("{id}")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Success",
                    content = {
                            @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FranchiseDTO.class))
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
    public ResponseEntity<FranchiseDTO> findById(@PathVariable int id){
        return ResponseEntity.ok(franchiseMapper.franchiseToFranchiseDTO(franchiseService.findById(id)));
    }

    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Created",
                    content = @Content
            )
    })
    public ResponseEntity add(@RequestBody FranchisePostDTO entity){
        franchiseService.add(franchiseMapper.franchisePostDTOToFranchise(entity));
        return ResponseEntity.created(URI.create("api/v1/franchise/?")).build();
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
    public ResponseEntity update(@RequestBody FranchiseDTO entity, @PathVariable int id){
        if(id != entity.getId()){return ResponseEntity.badRequest().build();}
        franchiseService.update(franchiseMapper.franchiseDTOToFranchise(entity));
        return ResponseEntity.accepted().build();
    }
}
