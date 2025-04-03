package org.contrum.Veterinaria.utils.validators;

import org.springframework.stereotype.Component;

@Component
public class ClinicalRecordValidator extends SimpleValidator {

    public long documentOrPetIdValidator(String value) throws Exception {
        return longValidator(value, "ID de la mascota o cedula");
    }

    public String reasonValidator(String value) throws Exception {
        return stringValidator(value, "razón de la consulta");
    }
}
