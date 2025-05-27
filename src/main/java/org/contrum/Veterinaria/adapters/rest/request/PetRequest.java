package org.contrum.Veterinaria.adapters.rest.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PetRequest {

    private String name;
    private int age;
    private String species;
    private String breed;
    private String color;
    private int size;
    private int weight;
    private long ownerDocument;

    @Override
    public String toString() {
        return "PetRequest{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", species='" + species + '\'' +
                ", breed='" + breed + '\'' +
                ", color='" + color + '\'' +
                ", size=" + size +
                ", weight=" + weight +
                ", ownerDocument=" + ownerDocument +
                '}';
    }
}
