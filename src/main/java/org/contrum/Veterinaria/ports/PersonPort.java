package org.contrum.Veterinaria.ports;

import jakarta.annotation.Nullable;
import org.contrum.Veterinaria.domain.models.Person;

public interface PersonPort {
    public void savePerson(Person person);

    public boolean existPerson(long document);

    public Person findById(long id);

    public Person findByDocument(Person person);

    @Nullable
    public Person findByDocument(long document);
}