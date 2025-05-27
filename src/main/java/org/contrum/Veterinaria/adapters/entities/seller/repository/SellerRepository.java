package org.contrum.Veterinaria.adapters.entities.seller.repository;

import org.contrum.Veterinaria.adapters.entities.seller.entity.SellerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<SellerEntity, Long> {

    public SellerEntity findBySellerId(long id);

}
