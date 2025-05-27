package org.contrum.Veterinaria.adapters.rest.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class InvoiceRequest {
    private long petId;
    private long orderId;
    private String productName;
    private long price;
    private int quantity;

    @Override
    public String toString() {
        return "InvoiceRequest{" +
                "petId=" + petId +
                ", orderId=" + orderId +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}