package org.contrum.Veterinaria.adapters.entities.seller.repository;

import org.contrum.Veterinaria.adapters.entities.seller.entity.SellerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<SellerEntity, Long> {

    /**
     * Find a seller by id.
     * @param sellerEntity the seller to look for.
     * @return the seller if found, null otherwise.
     */
    public SellerEntity findBySellerId(SellerEntity sellerEntity);

}
