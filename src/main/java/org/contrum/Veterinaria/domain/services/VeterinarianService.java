package org.contrum.Veterinaria.domain.services;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.contrum.Veterinaria.domain.models.ClinicalRecord;
import org.contrum.Veterinaria.domain.models.Order;
import org.contrum.Veterinaria.domain.models.Person;
import org.contrum.Veterinaria.domain.models.Pet;
import org.contrum.Veterinaria.ports.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Setter
@Getter
@NoArgsConstructor
@Service
public class VeterinarianService {

    @Autowired
    private PersonPort personPort;
    @Autowired
    private VeterinarianPort veterinarianPort;
    @Autowired
    private ClinicalRecordPort clinicalRecordPort;
    @Autowired
    private OrderPort orderPort;
    @Autowired
    private PetPort petPort;

    public void registerPetOwner(Person person) throws Exception {
        if (personPort.existPerson(person.getDocument())) {
            throw new Exception("Ya existe una persona con esa cedula!");
        }

        personPort.savePerson(person);
    }

    public void registerPet(Pet pet) throws Exception {
        if (!personPort.existPerson(pet.getOwnerDocument())) {
            throw new Exception("No existe una persona para asignar a la mascota con esa cedula!");
        }

        petPort.savePet(pet);
    }


    public void createClinicalRecord(ClinicalRecord record) throws Exception {
        if (!veterinarianPort.existVeterinarianByDocument(record.getVeterinarianId())) {
            throw new Exception("No existe un veterinario con esa cedula!");
        }

        if (!petPort.existPet(record.getPetId())) {
            throw new Exception("No existe una mascota con esa ID!");
        }

        record.setTimestamp(System.currentTimeMillis());
        clinicalRecordPort.saveClinicalRecord(record);
    }
}