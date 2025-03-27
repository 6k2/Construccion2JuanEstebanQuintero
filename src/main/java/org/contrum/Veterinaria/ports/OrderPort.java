package org.contrum.Veterinaria.ports;

import org.contrum.Veterinaria.domain.models.Order;

public interface OrderPort {
    public void saveOrder(Order order);

    public Order findByOrderId(Order order);
}