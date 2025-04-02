package org.contrum.Veterinaria.adapters.entities.users.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.contrum.Veterinaria.adapters.entities.person.entity.PersonEntity;

@Entity
@Table(name = "user")
@Getter @Setter
public class UserEntity {
    @Id
    @Column(name="id")
    private long userId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private PersonEntity person;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;
}
