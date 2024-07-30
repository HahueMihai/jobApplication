package com.jobApplication.jobApplication.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "patient")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @Column
    private String email;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<Medication> medications;

    @Builder.Default
    @Column
    private boolean takeMed = false;

}
