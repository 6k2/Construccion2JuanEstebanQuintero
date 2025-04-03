package org.contrum.Veterinaria.adapters.entities.order.repository;

import org.contrum.Veterinaria.adapters.entities.order.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    public OrderEntity findById(OrderEntity personEntity);

    List<OrderEntity> findByRecordId(long ownerId);

    List<OrderEntity> findByPetId(long id);

}
