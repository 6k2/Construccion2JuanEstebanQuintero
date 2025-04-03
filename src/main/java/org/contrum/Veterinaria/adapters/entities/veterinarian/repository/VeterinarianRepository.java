package org.contrum.Veterinaria.adapters.entities.veterinarian.repository;

import org.contrum.Veterinaria.adapters.entities.veterinarian.entity.VeterinarianEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VeterinarianRepository extends JpaRepository<VeterinarianEntity, Long> {

    public VeterinarianEntity findByVeterinarianId(VeterinarianEntity sellerEntity);

}
