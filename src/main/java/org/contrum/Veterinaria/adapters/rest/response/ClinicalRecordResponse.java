package org.contrum.Veterinaria.adapters.rest.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.contrum.Veterinaria.domain.models.ClinicalRecord;
import org.contrum.Veterinaria.domain.models.Person;

@Getter @Setter @NoArgsConstructor
public class ClinicalRecordResponse {
    private Long id;
    private long timestamp;
    private long veterinarianId;
    private long petId;
    private String reason;
    private String symptom;
    private String diagnostic;
    private String procedure;
    private String medicament;
    private String vaccination;
    private String allergicTo;
    private String procedureDetail;

    public ClinicalRecordResponse(ClinicalRecord record) {
        this.id = record.getId();
        this.timestamp = record.getTimestamp();
        this.veterinarianId = record.getVeterinarianId();
        this.petId = record.getPetId();
        this.reason = record.getReason();
        this.symptom = record.getSymptom();
        this.diagnostic = record.getDiagnostic();
        this.procedure = record.getProcedure();
        this.medicament = record.getMedicament();
        this.vaccination = record.getVaccination();
        this.allergicTo = record.getAllergicTo();
        this.procedureDetail = record.getProcedureDetail();
    }
}
