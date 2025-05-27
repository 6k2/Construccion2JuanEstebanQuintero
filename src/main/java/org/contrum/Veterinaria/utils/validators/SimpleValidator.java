package org.contrum.Veterinaria.utils.validators;

import org.contrum.Veterinaria.exceptions.InputsException;
import org.springframework.stereotype.Component;

@Component
public class SimpleValidator {

    /**
     * Validates that the provided string is not null or empty.
     *
     * @param value   the string value to validate
     * @param element the name of the element being validated, used in exception messages
     * @return the validated string if it is not null or empty
     * @throws Exception if the string is null or empty
     */
    public String stringValidator(String value, String element) throws InputsException {
        if (value == null || value.equals("")) {
            throw new InputsException(element + " no tiene un valor valido");
        }
        return value;
    }

    /**
     * Validates that the provided string is a valid long value.
     *
     * @param value   the string value to validate
     * @param element the name of the element being validated, used in exception messages
     * @return the validated long value if it is valid
     * @throws Exception if the string is not a valid long value
     */
    public Long longValidator(String value, String element) throws InputsException {
        try {
            long result = Long.parseLong(stringValidator(value, element));
            if (result < 0) {
                throw new InputsException(element + " no puede ser un valor negativo");
            }

            return result;
        } catch (Exception e) {
            throw new InputsException(element + " debe ser un valor numerico");
        }
    }

    /**
     * Validates that the provided string is a valid int value.
     *
     * @param value   the string value to validate
     * @param element the name of the element being validated, used in exception messages
     * @return the validated int value if it is valid
     * @throws Exception if the string is not a valid int value
     */
    public Integer intValidator(String value, String element) throws InputsException {
        try {
            return Integer.parseInt(stringValidator(value, element));
        } catch (Exception e) {
            throw new InputsException(element + " debe ser un valor numerico");
        }
    }

    /**
     * Validates that the provided string is a valid double value.
     *
     * @param value   the string value to validate
     * @param element the name of the element being validated, used in exception messages
     * @return the validated double value if it is valid
     * @throws Exception if the string is not a valid double value
     */

    // doubleValidator (not needed yet)
    public Double doubleValidator(String value, String element) throws InputsException {
        try {
            return Double.parseDouble(stringValidator(value, element));
        } catch (Exception e) {
            throw new InputsException(element + " debe ser un valor numerico");
        }
    }
}
