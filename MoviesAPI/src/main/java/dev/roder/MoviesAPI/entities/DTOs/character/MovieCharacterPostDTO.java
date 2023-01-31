package dev.roder.MoviesAPI.entities.DTOs.character;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieCharacterPostDTO {
    private String name;
    private String alias;
    private String gender;
    private String url;
}
