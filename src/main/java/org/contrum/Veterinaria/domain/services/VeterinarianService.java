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

    /**
     * Registers a new pet owner.
     * @param person the person to register.
     * @throws Exception if a person with the same document already exists.
     */
    public void registerPetOwner(Person person) throws Exception {
        if (personPort.existPerson(person.getDocument())) {
            throw new Exception("Ya existe una persona con esa cedula!");
        }

        personPort.savePerson(person);
    }

    /**
     * Registers a new pet.
     * @param pet the pet to register.
     * @throws Exception if there is no person with the given document.
     */
    public void registerPet(Pet pet) throws Exception {
        if (!personPort.existPerson(pet.getOwnerDocument())) {
            throw new Exception("No existe una persona para asignar a la mascota con esa cedula!");
        }

        petPort.savePet(pet);
    }

    /**
     * Finds a pet by its ID.
     *
     * @param id the ID of the pet to find.
     * @return the pet with the given ID, or null if no pet is found.
     */
    public Pet findPetById(long id) {
        return petPort.findById(id);
    }

    /**
     * Creates a clinical record for a pet.
     *
     * @param record the clinical record to be created, containing details such as
     *               the veterinarian ID, pet ID, reason for the consultation,
     *               symptoms, diagnosis, and other relevant information.
     * @throws Exception if the veterinarian ID does not correspond to an existing
     *                   veterinarian, or if the pet ID does not correspond to an
     *                   existing pet.
     */
    public void createClinicalRecord(ClinicalRecord record) throws Exception {
        if (!veterinarianPort.existVeterinarianById(record.getVeterinarianId())) {
            throw new Exception("No existe un veterinario con esa cedula!");
        }

        if (!petPort.existPet(record.getPetId())) {
            throw new Exception("No existe una mascota con esa ID!");
        }

        record.setTimestamp(System.currentTimeMillis());
        clinicalRecordPort.saveClinicalRecord(record);
    }

    /**
     * Creates a new order for a pet, given its clinical record ID, pet ID,
     * veterinarian ID, and the medicament to be prescribed.
     *
     * @param order the order to be created, including the clinical record ID,
     *              pet ID, veterinarian ID, and medicament.
     * @throws Exception if the clinical record ID does not correspond to an
     *                   existing clinical record, if the pet ID does not
     *                   correspond to an existing pet, or if the veterinarian
     *                   ID does not correspond to an existing veterinarian.
     */
    public void createOrder(Order order) throws Exception {
        if (!clinicalRecordPort.existsById(order.getRecordId())) {
            throw new Exception("No existe un registro clinico con esa ID!");
        }

        if (!veterinarianPort.existVeterinarianById(order.getVeterinarianId())) {
            throw new Exception("No existe un veterinario con esa cedula!");
        }

        if (!petPort.existPet(order.getPetId())) {
            throw new Exception("No existe una mascota con esa ID!");
        }

        order.setTimestamp(System.currentTimeMillis());
        orderPort.saveOrder(order);
    }
}