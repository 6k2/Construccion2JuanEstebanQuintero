package org.contrum.Veterinaria.adapters.entities.person.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "person") @Getter @Setter
public class PersonEntity {
    @Id
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
