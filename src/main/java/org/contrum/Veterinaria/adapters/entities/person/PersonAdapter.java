package org.contrum.Veterinaria.adapters.entities.person;

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

    @Override
    public void savePerson(Person person) {
        PersonEntity entity = this.personAdapter(person);
        personRepository.save(entity);
        person.setPersonId(entity.getId());
    }

    @Override
    public boolean existPerson(long document) {
        return personRepository.existsByDocument(document);
    }

    @Override
    public Person findByDocument(Person person) {
        PersonEntity userEntity = personRepository.findByDocument(this.personAdapter(person));
        return personAdapter(userEntity);
    }

    @Override
    public Person findByDocument(long document) {
        PersonEntity userEntity = personRepository.findByDocument(document);
        return personAdapter(userEntity);
    }

    public PersonEntity personAdapter(Person person) {
        PersonEntity personEntity = new PersonEntity();
        personEntity.setId(person.getPersonId());
        personEntity.setDocument(person.getDocument());
        personEntity.setName(person.getName());
        personEntity.setAge(person.getAge());
        personEntity.setRole(person.getRole().name());
        return personEntity;
    }

    private Person personAdapter(PersonEntity personEntity) {
        if (personEntity == null) {
            return null;
        }

        Person person = new Person();
        person.setPersonId(personEntity.getId());
        person.setName(person.getName());
        person.setDocument(personEntity.getDocument());
        person.setAge(person.getAge());
        person.setRole(person.getRole());
        return person;
    }
}
