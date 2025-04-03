package org.contrum.Veterinaria.adapters.entities.pet.repository;

import org.contrum.Veterinaria.adapters.entities.pet.entity.PetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetRepository extends JpaRepository<PetEntity, Long> {

    /**
     * Finds a PetEntity given another PetEntity as a parameter.
     * This method is used when you already have a PetEntity and you want to find the same one in the database.
     * @param petEntity the PetEntity to search for.
     * @return the PetEntity that matches the given one, or null if it doesn't exist.
     */
    public PetEntity findById(PetEntity petEntity);

    /**
     * Finds a list of PetEntities given a owner ID.
     * This method is used when you want to find all the pets that belong to a certain owner.
     * @param ownerId the ID of the owner to search for.
     * @return a list of PetEntities that belong to the given owner, or an empty list if no pets are found.
     */
    List<PetEntity> findByOwnerId(Long ownerId);
}
