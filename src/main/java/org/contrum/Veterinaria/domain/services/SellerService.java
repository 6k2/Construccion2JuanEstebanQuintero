/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package org.contrum.Veterinaria.domain.services;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.contrum.Veterinaria.adapters.entities.invoice.entity.InvoiceEntity;
import org.contrum.Veterinaria.domain.models.ClinicalRecord;
import org.contrum.Veterinaria.domain.models.Invoice;
import org.contrum.Veterinaria.domain.models.Person;
import org.contrum.Veterinaria.ports.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
