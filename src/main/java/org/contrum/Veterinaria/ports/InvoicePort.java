package org.contrum.Veterinaria.ports;

import org.contrum.Veterinaria.domain.models.Invoice;
import org.contrum.Veterinaria.domain.models.Order;

import java.util.List;

public interface InvoicePort {

    /**
     * Registers an invoice in the database.
     * <p>
     * Given an invoice, this method will save it in the database.
     *
     * @param invoice the invoice to be registered
     */
    public void saveInvoice(Invoice invoice);


    public Invoice findById(long id);


    /**
     * Finds a list of invoices by the given order ID.
     * <p>
     * This method searches for invoices associated with the specified order ID and returns them as a list.
     * If no invoices are found, an empty list is returned.
     *
     * @param id the order ID for which invoices are to be found
     * @return a list of invoices associated with the given order ID
     */
    public List<Invoice> findByOrderId(long id);

    /**
     * Finds a list of invoices by the given pet ID.
     * <p>
     * This method searches for invoices associated with the specified pet ID and returns them as a list.
     * If no invoices are found, an empty list is returned.
     *
     * @param id the pet ID for which invoices are to be found
     * @return a list of invoices associated with the given pet ID
     */
    public List<Invoice> findByPetId(long id);
}