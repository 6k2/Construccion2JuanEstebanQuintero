package org.contrum.Veterinaria.adapters.entities.order;

import org.contrum.Veterinaria.adapters.entities.clinicalrecord.entity.ClinicalRecordEntity;
import org.contrum.Veterinaria.adapters.entities.order.entity.OrderEntity;
import org.contrum.Veterinaria.adapters.entities.order.repository.OrderRepository;
import org.contrum.Veterinaria.adapters.entities.person.entity.PersonEntity;
import org.contrum.Veterinaria.adapters.entities.pet.entity.PetEntity;
import org.contrum.Veterinaria.adapters.entities.veterinarian.entity.VeterinarianEntity;
import org.contrum.Veterinaria.domain.models.Order;
import org.contrum.Veterinaria.ports.OrderPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderAdapter implements OrderPort {

    @Autowired
    private OrderRepository repository;

    /**
     * Saves the given order into the repository.
     * <p>
     * Converts the provided domain order object to an entity,
     * saves it in the database, and updates the order ID with the
     * generated ID from the entity.
     *
     * @param order the order to be saved
     */
    @Override
    public void saveOrder(Order order) {
        OrderEntity entity = this.orderAdapter(order);
        repository.save(entity);
        order.setId(entity.getId());
    }

    @Override
    public Order findById(long id) {
        OrderEntity userEntity = repository.findById(id).orElse(null);
        return orderAdapter(userEntity);
    }

    /**
     * Retrieves a list of orders associated with the specified clinical record ID.
     * <p>
     * This method queries the repository to find all orders linked to the given
     * clinical record ID and converts them from entity objects to domain objects.
     * If no orders are found, an empty list is returned.
     *
     * @param id the clinical record ID for which orders are to be retrieved
     * @return a list of orders associated with the specified clinical record ID
     */
    @Override
    public List<Order> findByClinicalRecordId(long id) {
        List<OrderEntity> orderEntities = repository.findByRecordId(id);
        return orderEntities.stream()
                .map(this::orderAdapter)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a list of orders associated with the specified pet ID.
     * <p>
     * This method queries the repository to find all orders linked to the given
     * pet ID and converts them from entity objects to domain objects.
     * If no orders are found, an empty list is returned.
     *
     * @param id the pet ID for which orders are to be retrieved
     * @return a list of orders associated with the specified pet ID
     */
    @Override
    public List<Order> findByPetId(long id) {
        List<OrderEntity> orderEntities = repository.findByPetId(id);
        return orderEntities.stream()
                .map(this::orderAdapter)
                .collect(Collectors.toList());
    }

    /**
     * Converts a domain order object to an entity object.
     * <p>
     * This method takes a domain order object as an argument and
     * creates a new entity object with the same values. The entity
     * object is then returned.
     *
     * @param order the order to be converted
     * @return the converted order entity
     */
    public OrderEntity orderAdapter(Order order) {
        OrderEntity orderEntity = new OrderEntity();

        orderEntity.setId(order.getId());
        orderEntity.setTimestamp(order.getTimestamp());

        ClinicalRecordEntity record = new ClinicalRecordEntity();
        record.setId(order.getRecordId());
        orderEntity.setRecord(record);

        PetEntity pet = new PetEntity();
        pet.setId(order.getPetId());
        orderEntity.setPet(pet);

        PersonEntity petOwner = new PersonEntity();
        petOwner.setId(order.getPetOwnerId());
        orderEntity.setPetOwner(petOwner);

        VeterinarianEntity veterinarian = new VeterinarianEntity();
        veterinarian.setVeterinarianId(order.getVeterinarianId());
        orderEntity.setVeterinarian(veterinarian);

        orderEntity.setMedicament(order.getMedicament());
        orderEntity.setCancelled(order.isCancelled());

        return orderEntity;
    }

    /**
     * Converts an order entity to a domain order object.
     * <p>
     * This method takes an order entity as an argument and
     * creates a new domain order object with the same values.
     * The order object is then returned.
     *
     * @param orderEntity the order entity to be converted
     * @return the converted order object, null if the input is null
     */
    private Order orderAdapter(OrderEntity orderEntity) {
        if (orderEntity == null) {
            return null;
        }

        Order order = new Order();

        order.setId(orderEntity.getId());
        order.setTimestamp(orderEntity.getTimestamp());
        order.setRecordId(orderEntity.getRecord().getId());

        if (orderEntity.getPet() != null) {
            order.setPetId(orderEntity.getPet().getId());
        }
        if (orderEntity.getPetOwner() != null) {
            order.setPetOwnerId(orderEntity.getPetOwner().getId());
        }
        if (orderEntity.getVeterinarian() != null) {
            order.setVeterinarianId(orderEntity.getVeterinarian().getVeterinarianId());
        }

        order.setMedicament(orderEntity.getMedicament());
        order.setCancelled(orderEntity.isCancelled());

        return order;
    }
}
