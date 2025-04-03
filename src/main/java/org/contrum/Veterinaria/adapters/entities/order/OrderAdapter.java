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

    @Override
    public void saveOrder(Order order) {
        OrderEntity entity = this.orderAdapter(order);
        repository.save(entity);
        order.setId(entity.getId());
    }

    @Override
    public Order findById(Order order) {
        OrderEntity userEntity = repository.findById(this.orderAdapter(order));
        return orderAdapter(userEntity);
    }

    @Override
    public List<Order> findByClinicalRecordId(long id) {
        List<OrderEntity> orderEntities = repository.findByRecordId(id);
        return orderEntities.stream()
                .map(this::orderAdapter)
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> findByPetId(long id) {
        List<OrderEntity> orderEntities = repository.findByPetId(id);
        return orderEntities.stream()
                .map(this::orderAdapter)
                .collect(Collectors.toList());
    }

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
