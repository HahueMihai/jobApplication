package com.jobApplication.jobApplication.repository;

import com.jobApplication.jobApplication.model.Medication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicationRepository extends CrudRepository<Medication, Integer> {
    List<Medication> findAll(Pageable pageable);

    @Query("SELECT med FROM Medication med JOIN med.patient p WHERE p.id = :id")
    List<Medication> findByPatientId(@Param("id") Integer id);
}
