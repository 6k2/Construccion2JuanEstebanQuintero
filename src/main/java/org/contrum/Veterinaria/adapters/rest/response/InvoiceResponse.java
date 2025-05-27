package org.contrum.Veterinaria.adapters.rest.response;

import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.contrum.Veterinaria.domain.models.Invoice;
import org.contrum.Veterinaria.domain.models.Order;

@Getter @Setter @NoArgsConstructor
public class InvoiceResponse {
    private long id;
    private long petId;
    private long petOwnerId;
    private Long orderId;
    private String productName;
    private long price;
    private int quantity;
    private long timestamp;

    public InvoiceResponse(Invoice invoice) {
        this.id = invoice.getId();
        this.petId = invoice.getPetId();
        this.petOwnerId = invoice.getPetOwnerId();
        this.orderId = invoice.getOrderId();
        this.productName = invoice.getProductName();
        this.price = invoice.getPrice();
        this.quantity = invoice.getQuantity();
        this.timestamp = invoice.getTimestamp();
    }
}
