package org.contrum.Veterinaria.ports;

import org.contrum.Veterinaria.domain.models.Invoice;
import org.contrum.Veterinaria.domain.models.Order;

import java.util.List;

public interface InvoicePort {
    public void saveInvoice(Invoice invoice);

    public Invoice findById(Invoice invoice);

    public List<Invoice> findByOrderId(long id);

    public List<Invoice> findByPetId(long id);
}