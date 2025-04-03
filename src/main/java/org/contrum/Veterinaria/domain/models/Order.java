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

    /**
     * Formats the timestamp of the order in a human-readable way, with format "d 'de' MMMM 'de' yyyy 'a las' HH:mm".
     * Example: "24 de noviembre de 2021 a las 14:30".
     *
     * @return the formatted timestamp
     */
    public String getFormattedTimeStampText() {
        SimpleDateFormat formatter = new SimpleDateFormat("d 'de' MMMM 'de' yyyy 'a las' HH:mm", new Locale("es", "ES"));
        return formatter.format(new Date(timestamp));
    }

    /**
     * Formats the timestamp of the order in a concise way using slashes and a 24-hour format.
     * The format is "dd/MM/yyyy HH:mm", which represents day, month, year, hour, and minute.
     * Locale is set to Spanish (Spain).
     *
     * @return the formatted timestamp
     */
    public String getFormattedTimeStampBars() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm", new Locale("es", "ES"));
        return formatter.format(new Date(timestamp));
    }
}
