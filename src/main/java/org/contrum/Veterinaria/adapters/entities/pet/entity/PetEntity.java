package org.contrum.Veterinaria.adapters.entities.pet.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.contrum.Veterinaria.adapters.entities.person.entity.PersonEntity;

@Entity
@Table(name = "pet")
@Getter @Setter
public class PetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private PersonEntity owner;

    @Column(name = "age", nullable = false)
    private int age;

    @Column(name = "species", nullable = false)
    private String species;

    @Column(name = "breed", nullable = false)
    private String breed;

    @Column(name = "color", nullable = false)
    private String color;

    @Column(name = "size", nullable = false)
    private int size;

    @Column(name = "weight", nullable = false)
    private double weight;
}