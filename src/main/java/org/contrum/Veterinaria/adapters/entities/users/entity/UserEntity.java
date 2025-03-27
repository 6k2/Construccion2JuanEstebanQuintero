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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @OneToOne
    @JoinColumn(name = "person_id")
    private PersonEntity person;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;
}
