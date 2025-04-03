package org.contrum.Veterinaria.utils.validators;

import org.springframework.stereotype.Component;

@Component
public class UserValidator extends SimpleValidator {

    /**
     * Valida que el nombre de usuario no sea nulo ni vac o.
     * @param value El valor a validar.
     * @return El valor validado.
     * @throws Exception Si el valor no es v lido.
     */
    public String userNameValidator(String value) throws Exception {
        return stringValidator(value, "nombre de usuario");
    }

    /**
     * Validates that the password is neither null nor empty.
     *
     * @param value The password to validate.
     * @return The validated password.
     * @throws Exception If the password is not valid.
     */
    public String passwordValidator(String value) throws Exception {
        return stringValidator(value, "contraseña de usuario");
    }
}
