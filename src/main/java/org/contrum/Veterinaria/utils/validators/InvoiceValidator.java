package org.contrum.Veterinaria.utils.validators;

import org.springframework.stereotype.Component;

@Component
public class InvoiceValidator extends SimpleValidator {

    public String productNameValidator(String value) throws Exception {
        return stringValidator(value, "Nombre del producto");
    }

    public long productPriceValidator(String value) throws Exception {
        return longValidator(value, "Precio del producto");
    }

    public int productQuantityValidator(String value) throws Exception {
        return intValidator(value, "Cantidad de productos");
    }
}
