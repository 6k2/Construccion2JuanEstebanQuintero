package org.contrum.Veterinaria.domain.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClinicalRecord {

    private long id;
    private long timestamp;
    private long veterinarianId;
    private long petId;
    private long orderId;
    private String reason;
    private String symptom;
    private String diagnostic;
    private String procedure;
    private String medicament;
    private String vaccination;
    private String allergicTo;
    private String procedureDetail;
}
