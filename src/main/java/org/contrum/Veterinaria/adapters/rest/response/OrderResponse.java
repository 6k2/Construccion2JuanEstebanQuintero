package org.contrum.Veterinaria.adapters.rest.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.contrum.Veterinaria.domain.models.ClinicalRecord;
import org.contrum.Veterinaria.domain.models.Order;

@Getter @Setter @NoArgsConstructor
public class OrderResponse {
    private long id;
    private long recordId;
    private long petId;
    private long petOwnerId;
    private long veterinarianId;
    private String medicament;
    private long timeStamp;
    private boolean canceled;

    public OrderResponse(Order order) {
        this.id = order.getId();
        this.recordId = order.getRecordId();
        this.petId = order.getPetId();
        this.petOwnerId = order.getPetOwnerId();
        this.veterinarianId = order.getVeterinarianId();
        this.medicament = order.getMedicament();
        this.timeStamp = order.getTimestamp();
        this.canceled = order.isCancelled();
    }
}
