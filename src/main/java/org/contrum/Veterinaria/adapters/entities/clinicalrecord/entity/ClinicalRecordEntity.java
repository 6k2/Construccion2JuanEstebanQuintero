package org.contrum.Veterinaria.adapters.entities.clinicalrecord.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.contrum.Veterinaria.adapters.entities.pet.entity.PetEntity;
import org.contrum.Veterinaria.adapters.entities.veterinarian.entity.VeterinarianEntity;

@Entity
@Table(name = "clinical_record")
@Getter @Setter
public class ClinicalRecordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "timestamp", nullable = false)
    private Long timestamp;

    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "veterinarian_id", referencedColumnName = "id", nullable = false)
    private VeterinarianEntity veterinarian;

    @ManyToOne
    @JoinColumn(name = "pet_id", referencedColumnName = "id", nullable = false)
    private PetEntity pet;

    @Column(name = "reason", nullable = false)
    private String reason;

    @Column(name = "symptom", nullable = false)
    private String symptom;

    @Column(name = "diagnostic", nullable = false)
    private String diagnostic;

    @Column(name = "procedure", nullable = false)
    private String procedure;

    @Column(name = "medicament")
    private String medicament;

    @Column(name = "vaccination")
    private String vaccination;

    @Column(name = "allergic_to")
    private String allergicTo;

    @Column(name = "procedure_detail")
    private String procedureDetail;

    @Column(name = "cancelled", nullable = false)
    private boolean cancelled;
}