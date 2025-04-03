package org.contrum.Veterinaria.adapters.entities.order.repository;

import org.contrum.Veterinaria.adapters.entities.order.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    /**
     * Finds an order by the given order entity.
     *
     * @param personEntity the order entity to be searched
     * @return the order if found, null otherwise
     */
    public OrderEntity findById(OrderEntity personEntity);

    /**
     * Finds a list of orders by the given clinical record ID.
     *
     * This method searches for orders associated with the specified clinical record ID
     * and returns them as a list. If no orders are found, an empty list is returned.
     *
     * @param ownerId the clinical record ID for which orders are to be found
     * @return a list of orders associated with the given clinical record ID
     */
    List<OrderEntity> findByRecordId(long ownerId);

    /**
     * Finds a list of orders by the given pet ID.
     *
     * This method searches for orders associated with the specified pet ID and
     * returns them as a list. If no orders are found, an empty list is returned.
     *
     * @param id the pet ID for which orders are to be found
     * @return a list of orders associated with the given pet ID
     */
    List<OrderEntity> findByPetId(long id);

}
