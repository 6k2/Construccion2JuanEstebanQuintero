package org.contrum.Veterinaria.adapters.entities.invoice.repository;

import org.contrum.Veterinaria.adapters.entities.invoice.entity.InvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<InvoiceEntity, Long> {

    public InvoiceEntity findById(InvoiceEntity personEntity);

    List<InvoiceEntity> findByOrderId(long orderId);

    List<InvoiceEntity> findByPetId(long id);

}
