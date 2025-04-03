package org.contrum.Veterinaria.adapters.entities.veterinarian.repository;

import org.contrum.Veterinaria.adapters.entities.veterinarian.entity.VeterinarianEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VeterinarianRepository extends JpaRepository<VeterinarianEntity, Long> {


    /**
     * Finds a VeterinarianEntity by its id in the VeterinarianEntity table
     * @param sellerEntity the VeterinarianEntity to search for
     * @return the found VeterinarianEntity, or null if not found
     */
    public VeterinarianEntity findByVeterinarianId(VeterinarianEntity sellerEntity);

}
