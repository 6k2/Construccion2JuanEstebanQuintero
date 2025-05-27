package org.contrum.Veterinaria.utils.validators;

import org.springframework.stereotype.Component;

@Component
public class PersonValidator extends SimpleValidator {

    /**
     * Verifica que el nombre de la persona sea un valor no vacio y con caracteres
     * validos.
     *
     * @param value el valor a verificar
     * @return el valor verificado
     * @throws Exception Si el valor no es valido
     */
    public String nameValidator(String value) throws Exception {
        return stringValidator(value, "nombre de la persona");
    }

    /**
     * Verifica que el documento de la persona sea un numero entero y un valor
     * positivo.
     *
     * @param value el valor a verificar
     * @return el valor verificado
     * @throws Exception Si el valor no es valido
     */
    public long documentValidator(String value) throws Exception {
        return longValidator(value, "numero de cedula");
    }

    public long idValidator(String value) throws Exception {
        return longValidator(value, "id de la persona");
    }

    /**
     * Verifica que la edad de la persona sea un valor numerico entero valido.
     *
     * @param value el valor a verificar
     * @return el valor verificado
     * @throws Exception Si el valor no es valido
     */
    public int ageValidator(String value) throws Exception {
        return intValidator(value, "edad de la persona");
    }
}
