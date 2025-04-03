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

    @Override
    public void savePet(Pet pet) {
        PetEntity petEntity = this.petAdapter(pet);
        petRepository.save(petEntity);
        pet.setId(petEntity.getId());
    }

    @Override
    public boolean existPet(long id) {
        return petRepository.existsById(id);
    }

    @Override
    public Pet findById(long id) {
        return petRepository.findById(id)
                .map(this::petAdapter)
                .orElse(null);
    }

    @Override
    public List<Pet> findPetsByOwnerId(Long ownerId) {
        List<PetEntity> petEntities = petRepository.findByOwnerId(ownerId);
        return petEntities.stream()
                .map(this::petAdapter)
                .collect(Collectors.toList());
    }

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
