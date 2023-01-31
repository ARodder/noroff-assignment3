package dev.roder.MoviesAPI.entities.DTOs.movie;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class MoviePostDTO {
    private String title;
    private String genre;
    private int releaseYear;
    private String director;
    private String picture;
    private String trailer;
}
