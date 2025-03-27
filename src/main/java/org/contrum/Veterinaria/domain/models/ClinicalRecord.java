package org.contrum.Veterinaria.domain.models;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ClinicalRecord {

    private long id;
    private long timestamp;
    private long orderId;
    private long petId;
    private long veterinarianId;
    private String reason;
    private String symptom;
    private String diagnostic;
    private String procedure;
    private String medicament;
    private String vaccination;
    private String allergicTo;
    private String procedureDetail;
    private boolean cancelled;

}
