package dev.roder.MoviesAPI.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@NoArgsConstructor
@Getter
@Setter
public class Character {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(length = 50,nullable = false)
    private String name;

    @Column(length = 50)
    private String alias;

    @Column(length=10)
    private String gender;

    @Column(length = 100)
    private String url;
    
}
