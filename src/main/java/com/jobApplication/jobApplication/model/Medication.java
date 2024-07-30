package com.jobApplication.jobApplication.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.Instant;

@Entity
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "medications")
public class Medication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    @Size(min = 1, max = 255)
    private String name;

    @Column
    @Size(min = 1, max = 255)
    private String dosage;

    @Column
    @Size(min = 1, max = 255)
    private String frequency;

    @Column
    private Instant startTime;

    @Column
    private Instant endTime;

    @ManyToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    private Patient patient;
}
