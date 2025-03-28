package org.contrum.Veterinaria.adapters.entities.order;

import org.contrum.Veterinaria.adapters.entities.order.entity.OrderEntity;
import org.contrum.Veterinaria.adapters.entities.order.repository.OrderRepository;
import org.contrum.Veterinaria.adapters.entities.person.entity.PersonEntity;
import org.contrum.Veterinaria.adapters.entities.pet.entity.PetEntity;
import org.contrum.Veterinaria.adapters.entities.veterinarian.entity.VeterinarianEntity;
import org.contrum.Veterinaria.domain.models.Order;
import org.contrum.Veterinaria.ports.OrderPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Order findByOrderId(Order order) {
        OrderEntity userEntity = repository.findById(this.orderAdapter(order));
        return orderAdapter(userEntity);
    }

    public OrderEntity orderAdapter(Order order) {
        OrderEntity orderEntity = new OrderEntity();

        orderEntity.setId(order.getId());
        orderEntity.setTimestamp(order.getTimestamp());

        PetEntity pet = new PetEntity();
        pet.setId(order.getPetId());
        orderEntity.setPet(pet);

        PersonEntity petOwner = new PersonEntity();
        petOwner.setDocument(order.getPetOwnerDocument());
        orderEntity.setPetOwner(petOwner);

        VeterinarianEntity veterinarian = new VeterinarianEntity();
        veterinarian.getUser().getPerson().setDocument(order.getVeterinarianDocument());
        orderEntity.setVeterinarian(veterinarian);

        orderEntity.setMedicament(order.getMedicament());

        return orderEntity;
    }

    private Order orderAdapter(OrderEntity orderEntity) {
        if (orderEntity == null) {
            return null;
        }

        Order order = new Order();

        order.setId(orderEntity.getId());
        order.setTimestamp(orderEntity.getTimestamp());

        if (orderEntity.getPet() != null) {
            order.setPetId(orderEntity.getPet().getId());
        }
        if (orderEntity.getPetOwner() != null) {
            order.setPetOwnerDocument(orderEntity.getPetOwner().getDocument());
        }
        if (orderEntity.getVeterinarian() != null) {
            order.setVeterinarianDocument(orderEntity.getVeterinarian().getUser().getPerson().getDocument());
        }

        order.setMedicament(orderEntity.getMedicament());

        return order;
    }
}
