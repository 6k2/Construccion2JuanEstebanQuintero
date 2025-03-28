package org.contrum.Veterinaria.adapters.entities.order.repository;

import org.contrum.Veterinaria.adapters.entities.order.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    public OrderEntity findById(OrderEntity personEntity);

}
