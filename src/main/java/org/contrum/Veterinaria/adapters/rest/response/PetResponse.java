package org.contrum.Veterinaria.adapters.rest.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.contrum.Veterinaria.domain.models.Person;
import org.contrum.Veterinaria.domain.models.Pet;

@Getter @Setter @NoArgsConstructor
public class PetResponse {
    private long id;
    private String name;
    private long ownerId;
    private long ownerDocument;
    private int age;
    private String species;
    private String breed;
    private String color;
    private int size;
    private double weight;


    public PetResponse(Pet pet) {
        this.id = pet.getId();
        this.name = pet.getName();
        this.ownerId = pet.getOwnerId();
        this.ownerDocument = pet.getOwnerDocument();
        this.age = pet.getAge();
        this.species = pet.getSpecies();
        this.breed = pet.getBreed();
        this.color = pet.getColor();
        this.size = pet.getSize();
        this.weight = pet.getWeight();
    }
}
