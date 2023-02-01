package dev.roder.MoviesAPI.entities.DTOs.movie;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class MovieUpdateDTO {
    private int id;
    private String title;
    private String genre;
    private int releaseYear;
    private String director;
    private String picture;
    private String trailer;
}
