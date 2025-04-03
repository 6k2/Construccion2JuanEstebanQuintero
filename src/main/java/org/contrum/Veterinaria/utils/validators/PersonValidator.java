package org.contrum.Veterinaria.utils.validators;

import org.springframework.stereotype.Component;

@Component
public class PersonValidator extends SimpleValidator {

    public String nameValidator(String value) throws Exception {
        return stringValidator(value, "nombre de la persona");
    }

    public long documentValidator(String value) throws Exception {
        return longValidator(value, "numero de cedula");
    }

    public int ageValidator(String value) throws Exception {
        return intValidator(value, "edad de la persona");
    }
}
