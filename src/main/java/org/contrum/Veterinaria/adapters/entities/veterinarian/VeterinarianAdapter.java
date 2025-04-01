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
    public void saveVeterinarian(Veterinarian seller) {
        VeterinarianEntity entity = this.veterinarianAdapter(seller);
        veterinarianRepository.save(entity);

        seller.setVeterinarianId(entity.getVeterinarianId());
    }

    @Override
    public boolean existVeterinarianByDocument(long document) {
        if (!personAdapter.existPerson(document)) {
            return false;
        }

        Person person = personAdapter.findByDocument(document);
        return person.getRole() == Person.Role.VETERINARIAN;
    }

    @Override
    public Veterinarian findByVeterinarianId(Veterinarian seller) {
        VeterinarianEntity adaptedSellerEntity = this.veterinarianAdapter(seller);
        VeterinarianEntity sellerEntity = veterinarianRepository.findByVeterinarianId(adaptedSellerEntity);

        return this.veterinarianAdapter(sellerEntity);
    }


    public Veterinarian veterinarianAdapter(VeterinarianEntity sellerEntity) {
        if (sellerEntity == null) {
            return null;
        }

        Veterinarian veterinarian = new Veterinarian();
        veterinarian.setVeterinarianId(sellerEntity.getVeterinarianId());
        veterinarian.setUserId(sellerEntity.getUser().getUserId());
        veterinarian.setPersonId(sellerEntity.getUser().getPerson().getId());
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
        personEntity.setId(user.getPersonId());
        veterinarianEntity.setVeterinarianId(user.getVeterinarianId());
        userEntity.setUserId(user.getUserId());
        userEntity.setPerson(personEntity);
        userEntity.setUserName(user.getUserName());
        userEntity.setPassword(user.getPassword());
        userEntity.setRole(user.getRole().name());

        veterinarianEntity.setUser(userEntity);
        return veterinarianEntity;
    }
}