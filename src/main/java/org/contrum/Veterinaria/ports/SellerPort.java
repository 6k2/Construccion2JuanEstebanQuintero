package org.contrum.Veterinaria.ports;

import org.contrum.Veterinaria.domain.models.Seller;

public interface SellerPort {
    public void saveSeller(Seller seller);

    public Seller findBySellerId(Seller seller);
}
