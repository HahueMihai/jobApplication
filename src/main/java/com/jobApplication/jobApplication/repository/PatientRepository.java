package com.jobApplication.jobApplication.repository;

import com.jobApplication.jobApplication.model.Patient;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends CrudRepository<Patient, Integer> {
    @Query("SELECT p FROM Patient p JOIN p.medications m WHERE m.name = :medicationName")
    List<Patient> findAllPatientsByMedName(@Param("medicationName") String medicationName, Pageable pageable);

    Patient findByEmail(String email);
}
