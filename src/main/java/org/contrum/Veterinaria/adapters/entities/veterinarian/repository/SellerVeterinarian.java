package org.contrum.Veterinaria.adapters.entities.veterinarian.repository;

import org.contrum.Veterinaria.adapters.entities.veterinarian.entity.VeterinarianEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerVeterinarian extends JpaRepository<VeterinarianEntity, Long> {

    public VeterinarianEntity findById(VeterinarianEntity sellerEntity);

}
