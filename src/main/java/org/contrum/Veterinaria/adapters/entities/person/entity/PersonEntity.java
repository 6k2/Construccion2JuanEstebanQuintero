package org.contrum.Veterinaria.adapters.entities.person.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "person") @Getter @Setter
public class PersonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "document")
    private long document;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    @Column(name = "role")
    private String role;
}
