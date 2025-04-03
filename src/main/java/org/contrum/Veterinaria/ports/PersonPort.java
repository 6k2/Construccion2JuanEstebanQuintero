package org.contrum.Veterinaria.ports;

import jakarta.annotation.Nullable;
import org.contrum.Veterinaria.domain.models.Person;

public interface PersonPort {
    /**
     * Saves a person in the database.
     * @param person the person to save.
     */
    public void savePerson(Person person);

    /**
     * Checks if a person exists in the database with the given document.
     *
     * @param document the document to search for.
     * @return true if a person with the given document exists, false otherwise.
     */
    public boolean existPerson(long document);

    /**
     * Finds a person by their ID.
     *
     * This method searches for a person in the database using the provided
     * unique identifier and returns the corresponding Person object if found.
     *
     * @param id the unique identifier of the person to find.
     * @return the Person object with the given ID, or null if no person is found.
     */
    public Person findById(long id);

    /**
     * Finds a person by their document.
     *
     * This method searches for a person in the database using the document
     * provided in the given Person object and returns the corresponding Person
     * object if found.
     *
     * @param person the Person object containing the document to search for.
     * @return the Person object with the given document, or null if no person is
     * found.
     */
    public Person findByDocument(Person person);

    /**
     * Finds a person by their document.
     *
     * This method searches for a person in the database using the given document
     * and returns the corresponding Person object if found.
     *
     * @param document the document to search for.
     * @return the Person object with the given document, or null if no person is
     * found.
     */
    @Nullable
    public Person findByDocument(long document);
}