package org.contrum.Veterinaria.ports;

import org.contrum.Veterinaria.domain.models.Order;

import java.util.List;

public interface OrderPort {
    /**
     * Saves the given order to the repository.
     * <p>
     * This method persists the order information in the database.
     * If the order is new, it will be created; if it already exists, it will be updated.
     *
     * @param order the order to be saved
     */
    public void saveOrder(Order order);

    public Order findById(long id);

    /**
     * Finds a list of orders by the given clinical record ID.
     * <p>
     * This method searches for orders associated with the specified clinical record ID and returns them as a list.
     * If no orders are found, an empty list is returned.
     *
     * @param id the clinical record ID for which orders are to be found
     * @return a list of orders associated with the given clinical record ID
     */
    public List<Order> findByClinicalRecordId(long id);

    public List<Order> findByPetId(long id);
}