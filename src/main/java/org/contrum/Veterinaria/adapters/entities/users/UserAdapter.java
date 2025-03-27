package org.contrum.Veterinaria.adapters.entities.users;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.contrum.Veterinaria.adapters.entities.person.PersonAdapter;
import org.contrum.Veterinaria.adapters.entities.person.entity.PersonEntity;
import org.contrum.Veterinaria.adapters.entities.users.entity.UserEntity;
import org.contrum.Veterinaria.adapters.entities.users.repository.UserRepository;
import org.contrum.Veterinaria.domain.models.Person;
import org.contrum.Veterinaria.domain.models.User;
import org.contrum.Veterinaria.ports.UserPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Setter
@Getter
@NoArgsConstructor
@Service
public class UserAdapter implements UserPort {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PersonAdapter personAdapter;

    @Override
    public boolean existUserName(String userName) {
        return userRepository.existsByUserName(userName);
    }

    @Override
    public void saveUser(User user) {
        UserEntity userEntity = userEntityAdapter(user);

        userRepository.save(userEntity);
        user.setUserId(userEntity.getUserId());
    }

    @Override
    public User findByPersonId(Person person) {
        PersonEntity personEntity = personAdapter.personAdapter(person);
        UserEntity userEntity = userRepository.findByPersonId(personEntity);
        User user = userAdapter(userEntity);
        return userAdapter(userEntity);
    }

    @Override
    public User findByUserName(User user) {
        UserEntity userEntity = userRepository.findByUserName(user.getUserName());
        return userAdapter(userEntity);
    }

    private User userAdapter(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }

        User user = new User();
        user.setUserId(userEntity.getUserId());
        user.setDocument(userEntity.getUserId());
        user.setPersonId(userEntity.getPerson().getId());
        user.setName(userEntity.getPerson().getName());
        user.setAge(userEntity.getPerson().getAge());
        user.setRole(userEntity.getRole());
        user.setUserName(userEntity.getUserName());
        user.setPassword(userEntity.getPassword());
        return user;
    }

    private UserEntity userEntityAdapter(User user) {
        PersonEntity personEntity = personAdapter.personAdapter(user);
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(user.getUserId());
        userEntity.setPerson(personEntity);
        userEntity.setUserName(user.getUserName());
        userEntity.setPassword(user.getPassword());
        userEntity.setRole(user.getRole().name());
        return userEntity;
    }
}