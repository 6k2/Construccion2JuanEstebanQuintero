/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package org.contrum.Veterinaria.domain.services;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.contrum.Veterinaria.domain.models.Invoice;
import org.contrum.Veterinaria.exceptions.NotFoundException;
import org.contrum.Veterinaria.ports.InvoicePort;
import org.contrum.Veterinaria.ports.PersonPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Service
public class SellerService {

    @Autowired
    private PersonPort personPort;
    @Autowired
    private InvoicePort invoicePort;

    /**
     * Registers an invoice in the database.
     * <p>
     * Given an invoice, this method will save it in the database.
     *
     * @param invoice the invoice to be registered
     */
    public void registerInvoice(Invoice invoice) {
        invoicePort.saveInvoice(invoice);
    }

    public Invoice getInvoice(long id) throws NotFoundException {
        Invoice invoice = invoicePort.findById(id);
        if (invoice == null) {
            throw new NotFoundException("No se encontró un registro clínico con la ID: " + id);
        }
        return invoice;
    }

    public List<Invoice> getInvoicesByPetId(long petId) throws NotFoundException {
        List<Invoice> invoices = invoicePort.findByPetId(petId);
        if (invoices == null || invoices.isEmpty()) {
            throw new NotFoundException("No se encontraron facturas para la mascota con ID: " + petId);
        }
        return invoices;
    }
}
