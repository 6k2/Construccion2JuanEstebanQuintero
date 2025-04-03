package org.contrum.Veterinaria.adapters.entities.users.repository;

import org.contrum.Veterinaria.adapters.entities.person.entity.PersonEntity;
import org.contrum.Veterinaria.adapters.entities.users.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    public boolean existsByUserName(String userName);

    public UserEntity findByPersonId(PersonEntity personEntity);

    public UserEntity findByUserName(String userName);
}