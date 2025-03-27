package org.contrum.Veterinaria.ports;

import org.contrum.Veterinaria.domain.models.Person;
import org.contrum.Veterinaria.domain.models.Pet;

public interface PetPort {
    public void savePet(Pet pet);

    public boolean existsPet(long id);

    public Person findByPersonId(Person person);
}