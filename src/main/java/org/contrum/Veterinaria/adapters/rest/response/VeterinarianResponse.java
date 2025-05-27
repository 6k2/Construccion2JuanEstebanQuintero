package org.contrum.Veterinaria.adapters.rest.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.contrum.Veterinaria.domain.models.Veterinarian;

@Getter
@Setter
@NoArgsConstructor
public class VeterinarianResponse extends UserResponse {

    public VeterinarianResponse(Veterinarian veterinarian) {
        super(veterinarian);
    }

}
