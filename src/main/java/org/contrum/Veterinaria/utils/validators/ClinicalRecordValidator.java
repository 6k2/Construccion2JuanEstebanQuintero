package org.contrum.Veterinaria.utils.validators;

import org.springframework.stereotype.Component;

@Component
public class ClinicalRecordValidator extends SimpleValidator {

    /**
     * Valida que el parámetro sea un número entero positivo, interpretándolo como
     * el ID de la mascota o cédula del dueño.
     *
     * @param value el valor a validar
     * @return el valor validado
     * @throws Exception si el valor no es un número entero positivo
     */
    public long documentOrPetIdValidator(String value) throws Exception {
        return longValidator(value, "ID de la mascota o cedula");
    }

    /**
     * Validates that the provided value is a non-empty string, interpreting it as
     * the reason for the consultation.
     *
     * @param value the value to validate
     * @return the validated value
     * @throws Exception if the value is null or empty
     */
    public String reasonValidator(String value) throws Exception {
        return stringValidator(value, "razón de la consulta");
    }

    public long idValidator(String value) throws Exception {
        return longValidator(value, "id del reporte clinico");
    }
}
