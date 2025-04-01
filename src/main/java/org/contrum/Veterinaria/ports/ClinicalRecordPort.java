package org.contrum.Veterinaria.ports;

import org.contrum.Veterinaria.domain.models.ClinicalRecord;

public interface ClinicalRecordPort {
    public void saveClinicalRecord(ClinicalRecord record);

    public ClinicalRecord findById(long record);
}