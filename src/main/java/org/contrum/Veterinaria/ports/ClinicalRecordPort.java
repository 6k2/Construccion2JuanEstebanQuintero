package org.contrum.Veterinaria.ports;

import org.contrum.Veterinaria.domain.models.ClinicalRecord;
import org.contrum.Veterinaria.domain.models.Person;

public interface ClinicalRecordPort {
    public void saveClinicalRecord(ClinicalRecord record);

    public ClinicalRecord findByClinicalRecordId(ClinicalRecord record);
}