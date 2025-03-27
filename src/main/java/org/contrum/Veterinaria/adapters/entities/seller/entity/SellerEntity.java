package org.contrum.Veterinaria.adapters.entities.seller.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.contrum.Veterinaria.adapters.entities.users.entity.UserEntity;

@Entity
@Table(name = "seller")
@Getter @Setter
public class SellerEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long sellerId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
