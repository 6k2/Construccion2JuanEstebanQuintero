package org.contrum.Veterinaria.adapters.rest.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ClinicalRecordRequest {

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

    @Override
    public String toString() {
        return "ClinicalRecordRequest{" +
                "veterinarianId=" + veterinarianId +
                ", petId=" + petId +
                ", reason='" + reason + '\'' +
                ", symptom='" + symptom + '\'' +
                ", diagnostic='" + diagnostic + '\'' +
                ", procedure='" + procedure + '\'' +
                ", medicament='" + medicament + '\'' +
                ", vaccination='" + vaccination + '\'' +
                ", allergicTo='" + allergicTo + '\'' +
                ", procedureDetail='" + procedureDetail + '\'' +
                '}';
    }
}
