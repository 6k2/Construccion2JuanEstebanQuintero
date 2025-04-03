package org.contrum.Veterinaria.adapters.entities.person.repository;

import org.contrum.Veterinaria.adapters.entities.person.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<PersonEntity, Long> {

    public boolean existsByDocument(long document);

    public PersonEntity findByDocument(PersonEntity personEntity);

    public PersonEntity findByDocument(long document);
}