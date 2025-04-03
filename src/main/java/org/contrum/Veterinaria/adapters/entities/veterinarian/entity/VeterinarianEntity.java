package org.contrum.Veterinaria.adapters.entities.veterinarian.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.contrum.Veterinaria.adapters.entities.users.entity.UserEntity;

@Entity
@Table(name = "veterinarians")
@Getter @Setter
public class VeterinarianEntity {
    @Id
    @Column(name = "id")
    private long veterinarianId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private UserEntity user;
}