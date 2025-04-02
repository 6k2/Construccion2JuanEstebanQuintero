package org.contrum.Veterinaria.ports;

import org.contrum.Veterinaria.domain.models.ClinicalRecord;

import java.util.List;

public interface ClinicalRecordPort {
    public void saveClinicalRecord(ClinicalRecord record);

    public boolean existsById(long id);

    public ClinicalRecord findById(long record);

    public List<ClinicalRecord> findByPetId(long petId);
}