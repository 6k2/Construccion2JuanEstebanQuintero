package org.contrum.Veterinaria.utils.validators;

import org.springframework.stereotype.Component;

@Component
public class PetValidator extends SimpleValidator {

    /**
     * Valida el nombre de la mascota. Debe ser un valor no nulo y no vacio.
     *
     * @param value El valor a validar
     * @return El valor validado
     * @throws Exception Si el valor no es valido
     */
    public String nameValidator(String value) throws Exception {
        return stringValidator(value, "nombre de la mascota");
    }

    /**
     * Valida el tipo de especie de la mascota. Debe ser un valor no nulo y no vacio.
     *
     * @param value El valor a validar
     * @return El valor validado
     * @throws Exception Si el valor no es valido
     */
    public String specieValidator(String value) throws Exception {
        return stringValidator(value, "tipo de especie");
    }

    /**
     * Valida el tipo de raza de la mascota. Debe ser un valor no nulo y no vacio.
     *
     * @param value El valor a validar
     * @return El valor validado
     * @throws Exception Si el valor no es valido
     */
    public String breedValidator(String value) throws Exception {
        return stringValidator(value, "tipo de raza");
    }

    /**
     * Valida el color de la mascota. Debe ser un valor no nulo y no vacio.
     *
     * @param value El valor a validar
     * @return El valor validado
     * @throws Exception Si el valor no es valido
     */
    public String colorValidator(String value) throws Exception {
        return stringValidator(value, "tipo de color");
    }

    /**
     * Valida el tama o de la mascota. Debe ser un n mero entero
     * positivo.
     *
     * @param value El valor a validar
     * @return El valor validado
     * @throws Exception Si el valor no es valido
     */
    public int sizeValidator(String value) throws Exception {
        return intValidator(value, "tamaño");
    }

    /**
     * Valida el peso de la mascota. Debe ser un n mero entero
     * positivo.
     *
     * @param value El valor a validar
     * @return El valor validado
     * @throws Exception Si el valor no es valido
     */
    public int weightValidator(String value) throws Exception {
        return intValidator(value, "peso");
    }

    /**
     * Valida la edad de la mascota. Debe ser un n mero entero
     * positivo.
     *
     * @param value El valor a validar
     * @return El valor validado
     * @throws Exception Si el valor no es valido
     */
    public int ageValidator(String value) throws Exception {
        return intValidator(value, "edad de la mascota");
    }
}
