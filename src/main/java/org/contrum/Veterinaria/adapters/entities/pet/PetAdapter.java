package org.contrum.Veterinaria.adapters.entities.pet;

import org.contrum.Veterinaria.adapters.entities.person.entity.PersonEntity;
import org.contrum.Veterinaria.adapters.entities.person.repository.PersonRepository;
import org.contrum.Veterinaria.adapters.entities.pet.entity.PetEntity;
import org.contrum.Veterinaria.adapters.entities.pet.repository.PetRepository;
import org.contrum.Veterinaria.domain.models.ClinicalRecord;
import org.contrum.Veterinaria.domain.models.Pet;
import org.contrum.Veterinaria.ports.PetPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetAdapter implements PetPort {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private PetRepository petRepository;

    /**
     * Saves a pet to the database.
     * @param pet the pet to save.
     */
    @Override
    public void savePet(Pet pet) {
        PetEntity petEntity = this.petAdapter(pet);
        petRepository.save(petEntity);
        pet.setId(petEntity.getId());
    }

    /**
     * Checks if a pet exists in the database by its ID.
     *
     * @param id the ID of the pet to check.
     * @return true if the pet exists, false otherwise.
     */
    @Override
    public boolean existPet(long id) {
        return petRepository.existsById(id);
    }

    /**
     * Finds a pet by its ID.
     *
     * @param id the ID of the pet to find.
     * @return the pet with the given ID, or null if no pet is found.
     */
    @Override
    public Pet findById(long id) {
        return petRepository.findById(id)
                .map(this::petAdapter)
                .orElse(null);
    }

    /**
     * Finds a list of pets by the given owner ID.
     *
     * @param ownerId the owner ID for which pets are to be found.
     * @return a list of pets associated with the given owner ID.
     */
    @Override
    public List<Pet> findPetsByOwnerId(Long ownerId) {
        List<PetEntity> petEntities = petRepository.findByOwnerId(ownerId);
        return petEntities.stream()
                .map(this::petAdapter)
                .collect(Collectors.toList());
    }

    /**
     * Adapts a Pet object to a PetEntity object.
     *
     * @param pet the Pet object to adapt.
     * @return the adapted PetEntity object.
     */
    public PetEntity petAdapter(Pet pet) {
        PetEntity petEntity = new PetEntity();
        PersonEntity petOwner = personRepository.findByDocument(pet.getOwnerDocument());

        petEntity.setId(pet.getId());
        petEntity.setName(pet.getName());
        petEntity.setOwner(petOwner);
        petEntity.setAge(pet.getAge());
        petEntity.setSpecies(pet.getSpecies());
        petEntity.setBreed(pet.getBreed());
        petEntity.setColor(pet.getColor());
        petEntity.setSize(pet.getSize());
        petEntity.setWeight(pet.getWeight());

        pet.setOwnerId(petOwner.getId());
        return petEntity;
    }

    /**
     * Adapts a PetEntity object to a Pet object.
     *
     * @param petEntity the PetEntity object to adapt.
     * @return the adapted Pet object, or null if the given PetEntity is null.
     */
    private Pet petAdapter(PetEntity petEntity) {
        if (petEntity == null) {
            return null;
        }

        Pet pet = new Pet();
        pet.setId(petEntity.getId());
        pet.setName(petEntity.getName());
        pet.setOwnerId(petEntity.getId());
        pet.setOwnerDocument(petEntity.getOwner().getId());
        pet.setAge(petEntity.getAge());
        pet.setSpecies(petEntity.getSpecies());
        pet.setBreed(petEntity.getBreed());
        pet.setColor(petEntity.getColor());
        pet.setSize(petEntity.getSize());
        pet.setWeight(petEntity.getWeight());
        return pet;
    }
}
