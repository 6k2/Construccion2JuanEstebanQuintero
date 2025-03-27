package org.contrum.Veterinaria.domain.models;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Order {

    private long id;
    private long petId;
    private long petOwnerDocument;
    private long veterinarianDocument;
    private String medicament;

    private long timestamp;

}
