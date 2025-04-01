package org.contrum.Veterinaria.domain.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pet {
    private long id;
    private String name;
    private long ownerDocument;
    private int age;
    private String species;
    private String breed;
    private String color;
    private int size;
    private double weight;
}
