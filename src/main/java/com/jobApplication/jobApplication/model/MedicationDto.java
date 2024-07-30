package com.jobApplication.jobApplication.model;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.Instant;

@Data
public class MedicationDto {

    private Integer id;

    private String name;

    private String dosage;

    private String frequency;

    private Instant startTime;

    private Instant endTime;

    private Integer patientId;

    public Medication toEntity(Patient patient) {
        return Medication.builder()
                .id(this.id)
                .name(this.name)
                .dosage(this.dosage)
                .frequency(this.frequency)
                .startTime(this.startTime)
                .endTime(this.endTime)
                .patient(patient)
                .build();
    }
}
