package org.contrum.Veterinaria.adapters.entities.person.repository;

import org.contrum.Veterinaria.adapters.entities.person.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<PersonEntity, Long> {

    /**
     * Returns whether a {@link PersonEntity} with the given document exists.
     *
     * @param document the document to search for
     * @return true if a {@link PersonEntity} with the given document exists, false otherwise
     */
    public boolean existsByDocument(long document);

    /**
     * Returns a {@link PersonEntity} associated with the given person.
     *
     * @param personEntity the person to search for
     * @return the {@link PersonEntity} associated with the given person, or null if not found
     */
    public PersonEntity findByDocument(PersonEntity personEntity);

    /**
     * Returns a {@link PersonEntity} associated with the given document.
     *
     * @param document the document to search for
     * @return the {@link PersonEntity} associated with the given document, or null if not found
     */
    public PersonEntity findByDocument(long document);
}