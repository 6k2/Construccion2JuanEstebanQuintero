package org.contrum.Veterinaria.ports;

import org.contrum.Veterinaria.domain.models.Order;

import java.util.List;

public interface OrderPort {
    public void saveOrder(Order order);

    public Order findById(Order order);

    public List<Order> findByClinicalRecordId(long id);

    public List<Order> findByPetId(long id);
}