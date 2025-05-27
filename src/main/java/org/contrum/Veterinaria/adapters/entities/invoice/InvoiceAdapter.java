package org.contrum.Veterinaria.adapters.entities.invoice;

import org.contrum.Veterinaria.adapters.entities.invoice.entity.InvoiceEntity;
import org.contrum.Veterinaria.adapters.entities.invoice.repository.InvoiceRepository;
import org.contrum.Veterinaria.adapters.entities.order.entity.OrderEntity;
import org.contrum.Veterinaria.adapters.entities.person.entity.PersonEntity;
import org.contrum.Veterinaria.adapters.entities.pet.entity.PetEntity;
import org.contrum.Veterinaria.domain.models.Invoice;
import org.contrum.Veterinaria.ports.InvoicePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceAdapter implements InvoicePort {

    @Autowired
    private InvoiceRepository repository;

    /**
     * Registers an invoice in the database.
     * <p>
     * Given an invoice, this method will save it in the database.
     *
     * @param invoice the invoice to be registered
     */
    @Override
    public void saveInvoice(Invoice invoice) {
        InvoiceEntity invoiceEntity = this.invoiceAdapter(invoice);
        repository.save(invoiceEntity);
        invoice.setId(invoiceEntity.getId());
    }

    @Override
    public Invoice findById(long id) {
        return repository.findById(id)
                .map(this::invoiceAdapter)
                .orElse(null);
    }

    /**
     * Finds a list of invoices by the given order ID.
     * <p>
     * This method searches for invoices associated with the specified order ID and returns them as a list.
     * If no invoices are found, an empty list is returned.
     *
     * @param id the order ID for which invoices are to be found
     * @return a list of invoices associated with the given order ID
     */
    @Override
    public List<Invoice> findByOrderId(long id) {
        List<InvoiceEntity> entities = repository.findByOrderId(id);
        return entities.stream()
                .map(this::invoiceAdapter)
                .collect(Collectors.toList());
    }

    /**
     * Finds a list of invoices by the given pet ID.
     * <p>
     * This method searches for invoices associated with the specified pet ID and returns them as a list.
     * If no invoices are found, an empty list is returned.
     *
     * @param id the pet ID for which invoices are to be found
     * @return a list of invoices associated with the given pet ID
     */
    @Override
    public List<Invoice> findByPetId(long id) {
        List<InvoiceEntity> entities = repository.findByPetId(id);
        return entities.stream()
                .map(this::invoiceAdapter)
                .collect(Collectors.toList());
    }

    /**
     * Converts a domain model invoice into an entity invoice.
     * <p>
     * This method takes a domain model invoice and converts it into an entity invoice
     * that can be used to interact with the database.
     *
     * @param invoice the domain model invoice to be converted
     * @return the converted entity invoice
     */
    public InvoiceEntity invoiceAdapter(Invoice invoice) {
        InvoiceEntity invoiceEntity = new InvoiceEntity();
        invoiceEntity.setId(invoice.getId());

        PetEntity petEntity = new PetEntity();
        petEntity.setId(invoice.getPetId());
        invoiceEntity.setPet(petEntity);

        PersonEntity petOwner = new PersonEntity();
        petOwner.setId(invoice.getPetOwnerId());
        invoiceEntity.setPetOwner(petOwner);

        if (invoice.getOrderId() != null) {
            OrderEntity orderEntity = new OrderEntity();
            orderEntity.setId(invoice.getOrderId());
            invoiceEntity.setOrder(orderEntity);
        }

        invoiceEntity.setProductName(invoice.getProductName());
        invoiceEntity.setPrice(invoice.getPrice());
        invoiceEntity.setQuantity(invoice.getQuantity());
        invoiceEntity.setTimestamp(invoice.getTimestamp());

        return invoiceEntity;
    }

    /**
     * Converts an entity invoice into a domain model invoice.
     * <p>
     * This method takes an entity invoice, typically fetched from the database,
     * and converts it into a domain model invoice for application use.
     * If the provided entity is null, the method will return null.
     *
     * @param invoiceEntity the entity invoice to be converted
     * @return the converted domain model invoice, or null if the input is null
     */
    private Invoice invoiceAdapter(InvoiceEntity invoiceEntity) {
        if (invoiceEntity == null) {
            return null;
        }

        Invoice invoice = new Invoice();
        invoice.setId(invoiceEntity.getId());
        invoice.setPetId(invoiceEntity.getPet().getId());
        invoice.setPetOwnerId(invoiceEntity.getPetOwner().getId());
        invoice.setProductName(invoiceEntity.getProductName());
        invoice.setPrice(invoiceEntity.getPrice());
        invoice.setQuantity(invoiceEntity.getQuantity());
        invoice.setTimestamp(invoiceEntity.getTimestamp());

        if (invoiceEntity.getOrder() != null) {
            invoice.setOrderId(invoiceEntity.getOrder().getId());
        }

        return invoice;
    }
}
