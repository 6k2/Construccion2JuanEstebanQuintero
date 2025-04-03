package org.contrum.Veterinaria.domain.models;

import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Getter
@Setter
public class ClinicalRecord {

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

    public String getFormattedTimeStampText() {
        SimpleDateFormat formatter = new SimpleDateFormat("d 'de' MMMM 'de' yyyy 'a las' HH:mm", new Locale("es", "ES"));
        return formatter.format(new Date(timestamp));
    }

    public String getFormattedTimeStampBars() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm", new Locale("es", "ES"));
        return formatter.format(new Date(timestamp));
    }
}
