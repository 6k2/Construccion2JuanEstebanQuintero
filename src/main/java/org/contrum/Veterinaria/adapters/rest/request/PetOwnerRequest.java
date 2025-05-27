package org.contrum.Veterinaria.adapters.rest.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PetOwnerRequest {
    private String name;
    private long document;
    private int age;

    @Override
    public String toString() {
        return "PetOwnerRequest{" +
                "name='" + name + '\'' +
                ", document=" + document +
                '}';
    }
}
