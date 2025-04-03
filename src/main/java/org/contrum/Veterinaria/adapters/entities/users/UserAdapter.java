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

    /**
     * Validates that a given username does not already exist in the database.
     *
     * @param userName The username to validate.
     * @return True if the username does not exist, false otherwise.
     */
    @Override
    public boolean existUserName(String userName) {
        return userRepository.existsByUserName(userName);
    }

    /**
     * Saves a user to the database.
     *
     * @param user The user to save.
     */
    @Override
    public void saveUser(User user) {
        UserEntity userEntity = userEntityAdapter(user);

        userRepository.save(userEntity);
        user.setId(userEntity.getUserId());
    }

    /**
     * Finds a user by the associated person entity.
     *
     * @param person The person object used to find the corresponding user.
     * @return The User object associated with the given person, or null if no user is found.
     */
    @Override
    public User findByPersonId(Person person) {
        PersonEntity personEntity = personAdapter.personAdapter(person);
        UserEntity userEntity = userRepository.findByPersonId(personEntity);
        User user = userAdapter(userEntity);
        return userAdapter(userEntity);
    }

    /**
     * Finds a user in the database by their username.
     *
     * @param user The user object containing the username to search for.
     * @return The User object matching the given username, or null if no user is found.
     */
    @Override
    public User findByUserName(User user) {
        UserEntity userEntity = userRepository.findByUserName(user.getUserName());
        return userAdapter(userEntity);
    }

    /**
     * Converts a UserEntity object to a User object.
     *
     * @param userEntity The UserEntity object to convert.
     * @return The converted User object, or null if the input is null.
     */
    private User userAdapter(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }

        User user = new User();
        user.setId(userEntity.getUserId());
        user.setDocument(userEntity.getUserId());
        user.setId(userEntity.getPerson().getId());
        user.setName(userEntity.getPerson().getName());
        user.setAge(userEntity.getPerson().getAge());
        user.setRole(userEntity.getRole());
        user.setUserName(userEntity.getUserName());
        user.setPassword(userEntity.getPassword());
        return user;
    }

    /**
     * Converts a User object to a UserEntity object.
     *
     * @param user The User object to convert.
     * @return The converted UserEntity object, or null if the input is null.
     */
    private UserEntity userEntityAdapter(User user) {
        PersonEntity personEntity = new PersonEntity();
        personEntity.setId(user.getId());

        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(user.getId());
        userEntity.setPerson(personEntity);
        userEntity.setUserName(user.getUserName());
        userEntity.setPassword(user.getPassword());
        userEntity.setRole(user.getRole().name());
        return userEntity;
    }
}