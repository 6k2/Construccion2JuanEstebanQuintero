package org.contrum.Veterinaria.utils.validators;

import org.springframework.stereotype.Component;

@Component
public class InvoiceValidator extends SimpleValidator {

    /**
     * Valida que el nombre del producto sea un valor no vacío o nulo.
     * @param value el valor a validar
     * @return el valor validado
     * @throws Exception si el valor no es válido
     */
    public String productNameValidator(String value) throws Exception {
        return stringValidator(value, "Nombre del producto");
    }

    /**
     * Valida que el precio del producto sea un valor numérico no vacío o nulo.
     * @param value el valor a validar
     * @return el valor validado como un long
     * @throws Exception si el valor no es válido o no es numérico
     */
    public long productPriceValidator(String value) throws Exception {
        return longValidator(value, "Precio del producto");
    }

    /**
     * Valida que la cantidad de productos sea un valor numérico no vacío o nulo.
     * @param value el valor a validar
     * @return el valor validado como un int
     * @throws Exception si el valor no es válido o no es numérico
     */
    public int productQuantityValidator(String value) throws Exception {
        return intValidator(value, "Cantidad de productos");
    }
}
