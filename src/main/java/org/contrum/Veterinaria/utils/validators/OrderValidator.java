package org.contrum.Veterinaria.utils.validators;

import org.springframework.stereotype.Component;

@Component
public class OrderValidator extends SimpleValidator {

    public long orderIdValidator(String value) throws Exception {
        return longValidator(value, "ID de la orden");
    }

    public long clientIdValidator(String value) throws Exception {
        return longValidator(value, "ID del cliente");
    }

    public long petIdValidator(String value) throws Exception {
        return longValidator(value, "ID de la mascota");
    }

    public String orderStatusValidator(String value) throws Exception {
        return stringValidator(value, "estado de la orden");
    }

    public String orderDetailsValidator(String value) throws Exception {
        return stringValidator(value, "detalles de la orden");
    }

    public long timestampValidator(String value) throws Exception {
        return longValidator(value, "timestamp");
    }

    public long recordIdValidator(String value) throws Exception {
        return longValidator(value, "ID del registro clínico");
    }

    public long veterinarianDocumentValidator(String value) throws Exception {
        return longValidator(value, "cedula del veterinario");
    }

    public long petOwnerDocumentValidator(String value) throws Exception {
        return longValidator(value, "cedula del dueño de la mascota");
    }

    public String medicamentValidator(String value) throws Exception {
        return stringValidator(value, "medicamento recetado");
    }
}
