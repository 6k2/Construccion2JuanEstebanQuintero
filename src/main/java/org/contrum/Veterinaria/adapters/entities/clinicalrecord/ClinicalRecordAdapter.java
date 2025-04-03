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

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClinicalRecordAdapter implements ClinicalRecordPort {

    @Autowired
    private ClinicalRecordRepository repository;

    /**
     * Saves a clinical record to the repository.
     *
     * <p>This method converts a domain model clinical record to an entity
     * and persists it in the database. It updates the ID of the
     * clinical record with the generated ID from the entity.
     *
     * @param record the clinical record to be saved
     */
    @Override
    public void saveClinicalRecord(ClinicalRecord record) {
        ClinicalRecordEntity entity = this.clinicalRecordAdapter(record);
        repository.save(entity);
        record.setId(entity.getId());
    }

    /**
     * Checks if a clinical record exists by its ID.
     *
     * <p>This method queries the repository to determine whether a clinical record
     * with the specified ID is present in the database.
     *
     * @param id the ID of the clinical record to check for existence
     * @return true if the clinical record exists, false otherwise
     */
    @Override
    public boolean existsById(long id) {
        return repository.existsById(id);
    }

    /**
     * Retrieves a clinical record by its ID.
     *
     * <p>This method searches the repository for a clinical record with the specified
     * ID. If found, it converts the entity into the domain model and returns it.
     *
     * @param record the ID of the clinical record to retrieve
     * @return the clinical record with the specified ID, or null if not found
     */
    @Override
    public ClinicalRecord findById(long record) {
        return repository.findById(record)
                .map(this::clinicalRecordAdapter)
                .orElse(null);
    }

    /**
     * Finds a list of clinical records associated with a given pet ID.
     * <p>
     * This method queries the repository to retrieve a list of clinical records
     * associated with the given pet ID. It converts the entities into the domain
     * model and returns them as a list.
     *
     * @param petId the pet ID for which to find clinical records
     * @return a list of clinical records associated with the given pet ID
     */
    @Override
    public List<ClinicalRecord> findByPetId(long petId) {
        List<ClinicalRecordEntity> petEntities = repository.findByPetId(petId);
        return petEntities.stream()
                .map(this::clinicalRecordAdapter)
                .collect(Collectors.toList());
    }

    /**
     * Converts a domain model ClinicalRecord to a ClinicalRecordEntity.
     *
     * <p>This method takes a domain model ClinicalRecord and converts it into
     * a ClinicalRecordEntity to be persisted in the database.
     *
     * @param clinicalRecord the ClinicalRecord to be converted
     * @return the converted ClinicalRecordEntity
     */
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

    /**
     * Converts a ClinicalRecordEntity to a ClinicalRecord domain model.
     *
     * <p>This method transforms a ClinicalRecordEntity, which represents the
     * persisted state of a clinical record in the database, into a
     * ClinicalRecord domain model used within the application. It handles
     * null checks for the entity and its relations, such as veterinarian
     * and pet, ensuring that the conversion process is robust.
     *
     * @param clinicalRecordEntity the ClinicalRecordEntity to be converted
     * @return the converted ClinicalRecord domain model, or null if the
     *         input entity is null
     */
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
