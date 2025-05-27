package org.contrum.Veterinaria.adapters.rest;

import org.contrum.Veterinaria.adapters.rest.request.InvoiceRequest;
import org.contrum.Veterinaria.adapters.rest.response.InvoiceResponse;
import org.contrum.Veterinaria.adapters.rest.response.OrderResponse;
import org.contrum.Veterinaria.domain.models.Invoice;
import org.contrum.Veterinaria.domain.models.Order;
import org.contrum.Veterinaria.domain.services.SellerService;
import org.contrum.Veterinaria.exceptions.BusinessException;
import org.contrum.Veterinaria.exceptions.InputsException;
import org.contrum.Veterinaria.exceptions.NotFoundException;
import org.contrum.Veterinaria.ports.OrderPort;
import org.contrum.Veterinaria.utils.validators.InvoiceValidator;
import org.contrum.Veterinaria.utils.validators.OrderValidator;
import org.contrum.Veterinaria.utils.validators.PetValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SellerController {

    @Autowired
    private SellerService sellerService;
    @Autowired
    private PetValidator petValidator;
    @Autowired
    private OrderValidator orderValidator;
    @Autowired
    private InvoiceValidator invoiceValidator;
    @Autowired
    private OrderPort orderPort;

    @PostMapping("/invoice")
    public ResponseEntity sellMedicament(@RequestBody InvoiceRequest request) {
        try {
            long petId = petValidator.idValidator(request.getPetId() + "");
            long orderId = orderValidator.idValidator(request.getOrderId() + "");
            String productName = invoiceValidator.productNameValidator(request.getProductName());
            long price = invoiceValidator.productPriceValidator(request.getPrice() + "");
            int quantity = invoiceValidator.productQuantityValidator(request.getQuantity() + "");

            Invoice invoice = new Invoice();
            invoice.setPetId(petId);
            invoice.setOrderId(orderId);
            invoice.setProductName(productName);
            invoice.setPrice(price);
            invoice.setQuantity(quantity);
            invoice.setTimestamp(System.currentTimeMillis());

            sellerService.registerInvoice(invoice);
            return new ResponseEntity("Se ha creado el invoice", HttpStatus.OK);
        } catch (BusinessException be) {
            return new ResponseEntity(be.getMessage(), HttpStatus.CONFLICT);
        } catch (InputsException ie) {
            return new ResponseEntity(ie.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/order/active/{id}")
    public ResponseEntity getActiveClinicalOrders(@PathVariable long id) {
        try {
            List<Order> orders = orderPort.findByPetId(id);
            if (orders.isEmpty()) {
                throw new Exception("No hay ordenes pendientes para esa mascota!");
            }

            List<OrderResponse> response = orders.stream()
                    .filter(order -> order != null && !order.isCancelled())
                    .map(OrderResponse::new).toList();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/invoice/{id}")
    public ResponseEntity getInvoiceById(@PathVariable long id) {
        try {
            Invoice invoice = sellerService.getInvoice(id);
            if (invoice == null) {
                throw new NotFoundException("No se encontró la factura con el ID: " + id);
            }

            InvoiceResponse invoiceResponse = new InvoiceResponse(invoice);
            return new ResponseEntity<>(invoiceResponse, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/invoices/{petId}")
    public ResponseEntity getInvoicesByPetId(@PathVariable long petId) {
        try {
            List<Invoice> invoice = sellerService.getInvoicesByPetId(petId);
            if (invoice == null) {
                throw new NotFoundException("No se encontró ninguna factura factura para la mascota con el ID: " + petId);
            }

            List<InvoiceResponse> responses = invoice.stream()
                    .map(InvoiceResponse::new)
                    .toList();
            return new ResponseEntity<>(responses, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
