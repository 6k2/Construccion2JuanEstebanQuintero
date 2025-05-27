package org.contrum.Veterinaria.adapters.rest.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.contrum.Veterinaria.domain.models.Seller;

@Getter
@Setter
@NoArgsConstructor
public class SellerResponse extends UserResponse {

    public SellerResponse(Seller seller) {
        super(seller);
    }

}
