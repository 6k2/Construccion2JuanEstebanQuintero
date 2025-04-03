package org.contrum.Veterinaria.utils.validators;

import org.springframework.stereotype.Component;

@Component
public class PetValidator extends SimpleValidator {

    public String nameValidator(String value) throws Exception {
        return stringValidator(value, "nombre de la mascota");
    }

    public String specieValidator(String value) throws Exception {
        return stringValidator(value, "tipo de especie");
    }

    public String breedValidator(String value) throws Exception {
        return stringValidator(value, "tipo de raza");
    }

    public String colorValidator(String value) throws Exception {
        return stringValidator(value, "tipo de color");
    }

    public int sizeValidator(String value) throws Exception {
        return intValidator(value, "tamaño");
    }

    public int weightValidator(String value) throws Exception {
        return intValidator(value, "peso");
    }

    public int ageValidator(String value) throws Exception {
        return intValidator(value, "edad de la mascota");
    }
}
