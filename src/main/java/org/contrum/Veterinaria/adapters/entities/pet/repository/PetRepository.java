package org.contrum.Veterinaria.adapters.entities.pet.repository;

import org.contrum.Veterinaria.adapters.entities.pet.entity.PetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetRepository extends JpaRepository<PetEntity, Long> {

    public PetEntity findById(PetEntity petEntity);

    List<PetEntity> findByOwnerId(Long ownerId);
}
