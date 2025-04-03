package org.contrum.Veterinaria.adapters.entities.veterinarian;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.contrum.Veterinaria.adapters.entities.person.PersonAdapter;
import org.contrum.Veterinaria.adapters.entities.person.entity.PersonEntity;
import org.contrum.Veterinaria.adapters.entities.users.entity.UserEntity;
import org.contrum.Veterinaria.adapters.entities.veterinarian.entity.VeterinarianEntity;
import org.contrum.Veterinaria.adapters.entities.veterinarian.repository.VeterinarianRepository;
import org.contrum.Veterinaria.domain.models.Person;
import org.contrum.Veterinaria.domain.models.Veterinarian;
import org.contrum.Veterinaria.ports.VeterinarianPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Setter
@Getter
@NoArgsConstructor
@Service
public class VeterinarianAdapter implements VeterinarianPort {

    @Autowired
    private VeterinarianRepository veterinarianRepository;

    @Autowired
    private PersonAdapter personAdapter;

    /**
     * Saves a veterinarian in the database.
     *
     * @param veterinarian the veterinarian to save.
     */
    @Override
    public void saveVeterinarian(Veterinarian veterinarian) {
        VeterinarianEntity entity = this.veterinarianAdapter(veterinarian);
        veterinarianRepository.save(entity);

        veterinarian.setId(entity.getVeterinarianId());
    }

    /**
     * Checks if a veterinarian exists by their document ID.
     *
     * @param document the document ID to check.
     * @return true if a veterinarian with the given document ID exists, false otherwise.
     */
    @Override
    public boolean existVeterinarianById(long document) {
        Person person = personAdapter.findById(document);
        return person != null && person.getRole() == Person.Role.VETERINARIAN;
    }

    /**
     * Finds a veterinarian by their ID.
     *
     * @param id the ID of the veterinarian to find.
     * @return the veterinarian with the given ID, or null if no veterinarian is found.
     */
    @Override
    public Veterinarian findById(long id) {
        return veterinarianRepository.findById(id)
                .map(this::veterinarianAdapter)
                .orElse(null);
    }


    /**
     * Converts a VeterinarianEntity to a Veterinarian domain model.
     *
     * @param sellerEntity the VeterinarianEntity to convert.
     * @return the corresponding Veterinarian domain model, or null if the input entity is null.
     */
    public Veterinarian veterinarianAdapter(VeterinarianEntity sellerEntity) {
        if (sellerEntity == null) {
            return null;
        }

        Veterinarian veterinarian = new Veterinarian();
        veterinarian.setId(sellerEntity.getVeterinarianId());
        veterinarian.setId(sellerEntity.getUser().getUserId());
        veterinarian.setId(sellerEntity.getUser().getPerson().getId());
        veterinarian.setDocument(sellerEntity.getUser().getPerson().getDocument());
        veterinarian.setName(sellerEntity.getUser().getPerson().getName());
        veterinarian.setAge(sellerEntity.getUser().getPerson().getAge());
        veterinarian.setRole(sellerEntity.getUser().getRole());
        veterinarian.setUserName(sellerEntity.getUser().getUserName());
        veterinarian.setPassword(sellerEntity.getUser().getPassword());
        return veterinarian;
    }

    /**
     * Converts a Veterinarian domain model to a VeterinarianEntity.
     * @param user the Veterinarian domain model to convert.
     * @return the corresponding VeterinarianEntity.
     */
    private VeterinarianEntity veterinarianAdapter(Veterinarian user) {
        PersonEntity personEntity = personAdapter.personAdapter(user);
        UserEntity userEntity = new UserEntity();
        VeterinarianEntity veterinarianEntity = new VeterinarianEntity();
        personEntity.setId(user.getId());
        veterinarianEntity.setVeterinarianId(user.getId());
        userEntity.setUserId(user.getId());
        userEntity.setPerson(personEntity);
        userEntity.setUserName(user.getUserName());
        userEntity.setPassword(user.getPassword());
        userEntity.setRole(user.getRole().name());

        veterinarianEntity.setUser(userEntity);
        return veterinarianEntity;
    }
}