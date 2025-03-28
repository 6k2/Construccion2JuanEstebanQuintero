package org.contrum.Veterinaria.ports;

import org.contrum.Veterinaria.domain.models.Person;

public interface PersonPort {
    public void savePerson(Person person);

    public boolean existPerson(long document);

    public Person findByPersonId(Person person);
}