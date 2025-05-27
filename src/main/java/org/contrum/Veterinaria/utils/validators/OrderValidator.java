package org.contrum.Veterinaria.utils.validators;

import org.springframework.stereotype.Component;

@Component
public class OrderValidator extends SimpleValidator {
    /**
     * Validates that the provided medicament name is a non-null and non-empty string.
     *
     * @param value The medicament name to validate.
     * @return The validated medicament name.
     * @throws Exception If the medicament name is null or empty.
     */
    public String medicamentValidator(String value) throws Exception {
        return stringValidator(value, "medicamento recetado");
    }

    public long idValidator(String value) throws Exception {
        return longValidator(value, "id de la orden");
    }
}
