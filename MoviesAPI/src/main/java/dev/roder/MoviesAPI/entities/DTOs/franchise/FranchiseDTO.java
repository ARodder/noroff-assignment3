package dev.roder.MoviesAPI.entities.DTOs.franchise;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FranchiseDTO {
    private int id;
    private String description;
    private String name;
    private Set<Integer> movies;

}
