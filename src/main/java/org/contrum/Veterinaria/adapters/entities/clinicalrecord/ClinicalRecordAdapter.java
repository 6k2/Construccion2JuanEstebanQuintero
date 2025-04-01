package org.contrum.Veterinaria.adapters.entities.clinicalrecord;

import org.contrum.Veterinaria.adapters.entities.clinicalrecord.entity.ClinicalRecordEntity;
import org.contrum.Veterinaria.adapters.entities.clinicalrecord.repository.ClinicalRecordRepository;
import org.contrum.Veterinaria.adapters.entities.order.entity.OrderEntity;
import org.contrum.Veterinaria.adapters.entities.pet.entity.PetEntity;
import org.contrum.Veterinaria.adapters.entities.veterinarian.entity.VeterinarianEntity;
import org.contrum.Veterinaria.domain.models.ClinicalRecord;
import org.contrum.Veterinaria.ports.ClinicalRecordPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClinicalRecordAdapter implements ClinicalRecordPort {

    @Autowired
    private ClinicalRecordRepository repository;

    @Override
    public void saveClinicalRecord(ClinicalRecord record) {
        ClinicalRecordEntity entity = this.clinicalRecordAdapter(record);
        repository.save(entity);
        record.setId(entity.getId());
    }

    @Override
    public ClinicalRecord findById(long record) {
        return repository.findById(record)
                .map(this::clinicalRecordAdapter)
                .orElse(null);
    }

    public ClinicalRecordEntity clinicalRecordAdapter(ClinicalRecord clinicalRecord) {
        ClinicalRecordEntity clinicalRecordEntity = new ClinicalRecordEntity();
        clinicalRecordEntity.setId(clinicalRecord.getId());
        clinicalRecordEntity.setTimestamp(clinicalRecord.getTimestamp());

        VeterinarianEntity veterinarian = new VeterinarianEntity();
        veterinarian.setVeterinarianId(clinicalRecord.getVeterinarianId());
        clinicalRecordEntity.setVeterinarian(veterinarian);

        PetEntity pet = new PetEntity();
        pet.setId(clinicalRecord.getPetId());
        clinicalRecordEntity.setPet(pet);

        if (clinicalRecord.getOrderId() != 0) {
            OrderEntity order = new OrderEntity();
            order.setId(clinicalRecord.getOrderId());
            clinicalRecordEntity.setOrder(order);
        }

        clinicalRecordEntity.setReason(clinicalRecord.getReason());
        clinicalRecordEntity.setSymptom(clinicalRecord.getSymptom());
        clinicalRecordEntity.setDiagnostic(clinicalRecord.getDiagnostic());
        clinicalRecordEntity.setProcedure(clinicalRecord.getProcedure());
        clinicalRecordEntity.setMedicament(clinicalRecord.getMedicament());
        clinicalRecordEntity.setVaccination(clinicalRecord.getVaccination());
        clinicalRecordEntity.setAllergicTo(clinicalRecord.getAllergicTo());
        clinicalRecordEntity.setProcedureDetail(clinicalRecord.getProcedureDetail());

        return clinicalRecordEntity;
    }

    private ClinicalRecord clinicalRecordAdapter(ClinicalRecordEntity clinicalRecordEntity) {
        if (clinicalRecordEntity == null) {
            return null;
        }

        ClinicalRecord clinicalRecord = new ClinicalRecord();
        clinicalRecord.setId(clinicalRecordEntity.getId());
        clinicalRecord.setTimestamp(clinicalRecordEntity.getTimestamp());

        if (clinicalRecordEntity.getVeterinarian() != null) {
            clinicalRecord.setVeterinarianId(clinicalRecordEntity.getVeterinarian().getVeterinarianId());
        }
        if (clinicalRecordEntity.getPet() != null) {
            clinicalRecord.setPetId(clinicalRecordEntity.getPet().getId());
        }
        if (clinicalRecordEntity.getOrder() != null) {
            clinicalRecord.setOrderId(clinicalRecordEntity.getOrder().getId());
        }

        clinicalRecord.setReason(clinicalRecordEntity.getReason());
        clinicalRecord.setSymptom(clinicalRecordEntity.getSymptom());
        clinicalRecord.setDiagnostic(clinicalRecordEntity.getDiagnostic());
        clinicalRecord.setProcedure(clinicalRecordEntity.getProcedure());
        clinicalRecord.setMedicament(clinicalRecordEntity.getMedicament());
        clinicalRecord.setVaccination(clinicalRecordEntity.getVaccination());
        clinicalRecord.setAllergicTo(clinicalRecordEntity.getAllergicTo());
        clinicalRecord.setProcedureDetail(clinicalRecordEntity.getProcedureDetail());

        return clinicalRecord;
    }
}
