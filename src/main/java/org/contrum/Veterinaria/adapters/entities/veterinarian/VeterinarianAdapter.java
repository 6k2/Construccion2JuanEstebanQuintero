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

    @Override
    public void saveVeterinarian(Veterinarian veterinarian) {
        VeterinarianEntity entity = this.veterinarianAdapter(veterinarian);
        veterinarianRepository.save(entity);

        veterinarian.setId(entity.getVeterinarianId());
    }

    @Override
    public boolean existVeterinarianById(long document) {
        Person person = personAdapter.findById(document);
        return person != null && person.getRole() == Person.Role.VETERINARIAN;
    }

    @Override
    public Veterinarian findById(long id) {
        return veterinarianRepository.findById(id)
                .map(this::veterinarianAdapter)
                .orElse(null);
    }


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