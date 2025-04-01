package org.contrum.Veterinaria.ports;

import org.contrum.Veterinaria.domain.models.Pet;

import java.util.List;

public interface PetPort {
    public void savePet(Pet pet);

    public boolean existPet(long id);

    public Pet findById(Pet person);


    List<Pet> findPetsByOwnerId(Long ownerId);
}