package org.contrum.Veterinaria.adapters.rest;

import org.contrum.Veterinaria.adapters.rest.response.OrderResponse;
import org.contrum.Veterinaria.domain.models.Order;
import org.contrum.Veterinaria.domain.services.VeterinarianService;
import org.contrum.Veterinaria.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BaseStoreManagerController {

    @Autowired
    protected VeterinarianService veterinarianService;

    @GetMapping("/order/{id}")
    public ResponseEntity getOrderById(@PathVariable long id) {
        try {
            Order order = veterinarianService.getOrderById(id);
            if (order == null) {
                throw new NotFoundException("No existe una orden con la ID: " + id);
            }

            OrderResponse orderResponse = new OrderResponse(order);
            return new ResponseEntity(orderResponse, HttpStatus.FOUND);
        } catch (NotFoundException NFe) {
            return new ResponseEntity(NFe.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/orders/{petId}")
    public ResponseEntity getOrderByPetId(@PathVariable long petId) {
        try {
            List<Order> orders = veterinarianService.getOrdersByPetId(petId);

            List<OrderResponse> responses = orders.stream()
                    .map(OrderResponse::new)
                    .toList();
            return new ResponseEntity(responses, HttpStatus.FOUND);
        } catch (NotFoundException NFe) {
            return new ResponseEntity(NFe.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
