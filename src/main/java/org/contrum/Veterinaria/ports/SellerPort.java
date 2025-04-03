package org.contrum.Veterinaria.ports;

import org.contrum.Veterinaria.domain.models.Seller;

public interface SellerPort {
    /**
     * Saves a seller to the database.
     * <p>
     * Given a seller, this method will save it in the database.
     *
     * @param seller the seller to be saved
     */
    public void saveSeller(Seller seller);

    public Seller findBySellerId(Seller seller);
}
