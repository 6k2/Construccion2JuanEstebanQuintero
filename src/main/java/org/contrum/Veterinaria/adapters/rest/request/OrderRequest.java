package org.contrum.Veterinaria.adapters.rest.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderRequest {

    private long recordId;
    private long petId;
    private long veterinarianId;
    private String medicament;

    @Override
    public String toString() {
        return "OrderRequest{" +
                ", recordId=" + recordId +
                ", petId=" + petId +
                ", veterinarianId=" + veterinarianId +
                ", medicament='" + medicament + '\'' +
                '}';
    }
}
