package eu.sternenfighter.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column
    private String name;
}
