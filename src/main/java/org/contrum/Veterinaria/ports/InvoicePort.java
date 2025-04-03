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


    /**
     * Finds an invoice by the given invoice object.
     * <p>
     * If the invoice exists in the database, this method will return the invoice.
     * Otherwise, it will return null.
     *
     * @param invoice the invoice to be searched
     * @return the invoice if found, null otherwise
     */
    public Invoice findById(Invoice invoice);


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