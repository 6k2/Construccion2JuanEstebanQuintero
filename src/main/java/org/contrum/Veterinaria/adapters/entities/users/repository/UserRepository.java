package org.contrum.Veterinaria.adapters.entities.users.repository;

import org.contrum.Veterinaria.adapters.entities.person.entity.PersonEntity;
import org.contrum.Veterinaria.adapters.entities.users.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    /**
     * Determines if a user exists by username.
     *
     * @param userName Username of the user.
     * @return true if the user exists, false otherwise.
     */
    public boolean existsByUserName(String userName);


    /**
     * Finds a user by person ID.
     *
     * @param personEntity The person.
     * @return The user entity found.
     */
    public UserEntity findByPersonId(PersonEntity personEntity);


    /**
     * Finds a user by username.
     *
     * @param userName Username of the user.
     * @return The user entity found.
     */
    public UserEntity findByUserName(String userName);
}