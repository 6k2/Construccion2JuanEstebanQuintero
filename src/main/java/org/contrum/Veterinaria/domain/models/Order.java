package org.contrum.Veterinaria.domain.models;

import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Getter @Setter
public class Order {

    private long id;
    private long recordId;
    private long petId;
    private long petOwnerId;
    private long veterinarianId;
    private String medicament;

    private long timestamp;

    private boolean cancelled;

    public String getFormattedTimeStampText() {
        SimpleDateFormat formatter = new SimpleDateFormat("d 'de' MMMM 'de' yyyy 'a las' HH:mm", new Locale("es", "ES"));
        return formatter.format(new Date(timestamp));
    }

    public String getFormattedTimeStampBars() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm", new Locale("es", "ES"));
        return formatter.format(new Date(timestamp));
    }
}
