package org.contrum.Veterinaria.domain.models;

import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Getter @Setter
public class Invoice {

    private long id;
    private long petId;
    private long petOwnerId;
    @Nullable
    private Long orderId;
    private String productName;
    private long price;
    private int quantity;

    private long timestamp;

    public String getFormattedTimeStampText() {
        return new SimpleDateFormat("dd/MM/yyyy HH:mm", new Locale("es", "ES")).format(new Date(this.getTimestamp()));
    }

    public String getFormattedTimeStampBars() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm", new Locale("es", "ES"));
        return formatter.format(new Date(timestamp));
    }
}
