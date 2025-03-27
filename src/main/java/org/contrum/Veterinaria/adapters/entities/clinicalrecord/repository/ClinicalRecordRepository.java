package org.contrum.Veterinaria.adapters.entities.clinicalrecord.repository;

import org.contrum.Veterinaria.adapters.entities.clinicalrecord.entity.ClinicalRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClinicalRecordRepository extends JpaRepository<ClinicalRecordEntity, Long> {

    public ClinicalRecordEntity findById(ClinicalRecordEntity personEntity);

}
