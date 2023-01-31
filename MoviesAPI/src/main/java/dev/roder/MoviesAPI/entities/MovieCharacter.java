package dev.roder.MoviesAPI.entities;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "character")
public class MovieCharacter {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(length = 50,nullable = false)
    private String name;

    @Column(length = 50)
    private String alias;

    @Column(length=10)
    private String gender;

    @Column(length = 300)
    private String url;

    @ManyToMany(mappedBy = "characters")
    private Set<Movie> movies;
    
}
