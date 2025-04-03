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

    @Override
    public void saveInvoice(Invoice invoice) {
        InvoiceEntity invoiceEntity = this.invoiceAdapter(invoice);
        repository.save(invoiceEntity);
        invoice.setId(invoiceEntity.getId());
    }

    @Override
    public Invoice findById(Invoice invoice) {
        return repository.findById(invoice.getId())
                .map(this::invoiceAdapter)
                .orElse(null);
    }

    @Override
    public List<Invoice> findByOrderId(long id) {
        List<InvoiceEntity> entities = repository.findByOrderId(id);
        return entities.stream()
                .map(this::invoiceAdapter)
                .collect(Collectors.toList());
    }

    @Override
    public List<Invoice> findByPetId(long id) {
        List<InvoiceEntity> entities = repository.findByPetId(id);
        return entities.stream()
                .map(this::invoiceAdapter)
                .collect(Collectors.toList());
    }

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
