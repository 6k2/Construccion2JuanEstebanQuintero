package org.contrum.Veterinaria.ports;

import org.contrum.Veterinaria.domain.models.Pet;

import java.util.List;

public interface PetPort {
    /**
     * Registers a new pet in the system.
     * @param pet the pet to register.
     */
    public void savePet(Pet pet);

    /**
     * Determines if a pet exists in the system with the given ID.
     * @param id the pet ID to search for.
     * @return true if the pet exists, false otherwise.
     */
    public boolean existPet(long id);

    /**
     * Finds a pet by its ID.
     * <p>
     * This method searches for a pet in the system using the given ID and returns it.
     * If no pet is found with the specified ID, it returns null.
     *
     * @param id the ID of the pet to find
     * @return the pet with the given ID, or null if no pet is found
     */
    public Pet findById(long id);

    /**
     * Finds a list of pets by the given owner ID.
     * <p>
     * This method searches for pets associated with the specified owner ID
     * and returns them as a list. If no pets are found, an empty list is returned.
     *
     * @param ownerId the owner ID for which pets are to be found
     * @return a list of pets associated with the given owner ID
     */
    List<Pet> findPetsByOwnerId(Long ownerId);
}