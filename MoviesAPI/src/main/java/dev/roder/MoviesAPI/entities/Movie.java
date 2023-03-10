package dev.roder.MoviesAPI.entities;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@NoArgsConstructor
@Getter
@Setter
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 50,nullable = false)
    private String title;

    @Column(length = 50)
    private String genre;

    @Column(nullable = false)
    private Integer releaseYear;

    @Column(length = 50,nullable = false)
    private String director;

    @Column(length = 300)
    private String picture;

    @Column(length = 200)
    private String trailer;

    @ManyToMany
    @JoinTable(name = "movie_characters", joinColumns = @JoinColumn(name="movie_id"),inverseJoinColumns = @JoinColumn(name="character_id"))
    private Set<MovieCharacter> characters;

    @ManyToOne
    private Franchise franchise;


    
}
