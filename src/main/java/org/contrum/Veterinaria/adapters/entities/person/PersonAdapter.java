package org.contrum.Veterinaria.adapters.entities.person;

import jakarta.annotation.Nullable;
import org.contrum.Veterinaria.adapters.entities.person.entity.PersonEntity;
import org.contrum.Veterinaria.adapters.entities.person.repository.PersonRepository;
import org.contrum.Veterinaria.domain.models.Person;
import org.contrum.Veterinaria.ports.PersonPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonAdapter implements PersonPort {

    @Autowired
    private PersonRepository personRepository;

    /**
     * Save a person in the database.
     *
     * @param person the person to save
     */
    @Override
    public void savePerson(Person person) {
        PersonEntity entity = this.personAdapter(person);
        personRepository.save(entity);
        person.setId(entity.getId());
    }

    /**
     * Checks if a person exists in the database with the given document.
     *
     * @param document the document number to check for existence
     * @return true if a person with the given document exists, false otherwise
     */
    @Override
    public boolean existPerson(long document) {
        return personRepository.existsByDocument(document);
    }

    /**
     * Find a person by id.
     *
     * @param id the id of the person to search for
     * @return the person with the given id, or null if no such person exists
     */
    @Override
    public Person findById(long id) {
        return personRepository.findById(id)
                .map(this::personAdapter)
                .orElse(null);
    }

    /**
     * Finds a person in the database using the provided person details.
     *
     * @param person the person object containing details such as document number
     * @return the matched person from the database, or null if no match is found
     */
    @Override
    public Person findByDocument(Person person) {
        PersonEntity userEntity = personRepository.findByDocument(this.personAdapter(person));
        return personAdapter(userEntity);
    }

    /**
     * Finds a person in the database using the provided document number.
     *
     * @param document the document number to search for
     * @return the matched person from the database, or null if no match is found
     */
    @Override @Nullable
    public Person findByDocument(long document) {
        PersonEntity userEntity = personRepository.findByDocument(document);
        return personAdapter(userEntity);
    }

    /**
     * Converts a Person object to a PersonEntity object.
     *
     * @param person the Person object to convert
     * @return a PersonEntity object containing the same details as the input Person
     */
    public PersonEntity personAdapter(Person person) {
        PersonEntity personEntity = new PersonEntity();
        personEntity.setId(person.getId());
        personEntity.setDocument(person.getDocument());
        personEntity.setName(person.getName());
        personEntity.setAge(person.getAge());
        personEntity.setRole(person.getRole().name());
        return personEntity;
    }

    /**
     * Converts a PersonEntity object to a Person object.
     *
     * @param personEntity the PersonEntity object to convert
     * @return a Person object containing the same details as the input PersonEntity,
     *         or null if the input is null
     */
    private Person personAdapter(PersonEntity personEntity) {
        if (personEntity == null) {
            return null;
        }

        Person person = new Person();
        person.setId(personEntity.getId());
        person.setName(personEntity.getName());
        person.setDocument(personEntity.getDocument());
        person.setAge(personEntity.getAge());
        person.setRole(personEntity.getRole());
        return person;
    }
}
