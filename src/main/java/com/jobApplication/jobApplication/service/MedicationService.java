package com.jobApplication.jobApplication.service;

import com.jobApplication.jobApplication.model.Medication;
import com.jobApplication.jobApplication.model.MedicationDto;
import com.jobApplication.jobApplication.model.Patient;
import com.jobApplication.jobApplication.repository.MedicationRepository;
import com.jobApplication.jobApplication.repository.PatientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicationService {
    @Autowired
    private MedicationRepository medicationRepository;

    @Autowired
    private PatientService patientService;

    private final Logger logger = LoggerFactory.getLogger(MedicationService.class);

    public Medication saveMedication(MedicationDto medicationDto) {
        logger.info("Medication saved successfully.");
        Patient patient = patientService.findById(medicationDto.getPatientId());
        if(patient==null){
            logger.error("Patient with id: {} does not exist!", medicationDto.getPatientId());
            throw new SecurityException("Patient does not exist!");
        }
        Medication medication = medicationDto.toEntity(patient);
        return medicationRepository.save(medication);
    }

    public Medication updateMedication(Medication medicationDto) {
        Medication medication = medicationRepository.findById(medicationDto.getId())
                .orElseThrow(() -> {
                    logger.error("Medication with id: {} does not exist!", medicationDto.getId());
                    return new SecurityException("Medication does not exist!");
                });
        if (medicationDto.getName() != null && !medicationDto.getName().equals(medication.getName())) {
            medication.setName(medicationDto.getName());
        }
        if (medicationDto.getDosage() != null && !medicationDto.getDosage().equals(medication.getDosage())) {
            medication.setDosage(medicationDto.getDosage());
        }
        if (medicationDto.getFrequency() != null && !medicationDto.getFrequency().equals(medication.getFrequency())) {
            medication.setFrequency(medicationDto.getFrequency());
        }
        if(medicationDto.getStartTime()!=null && !medicationDto.getStartTime().equals(medication.getStartTime())){
            medication.setStartTime(medicationDto.getStartTime());
        }
        if(medicationDto.getEndTime()!=null && !medicationDto.getEndTime().equals(medication.getEndTime())){
            medication.setEndTime(medicationDto.getEndTime());
        }
        if(medicationDto.getPatient()!=null){
            medication.setPatient(medication.getPatient());
        }
        logger.info("Medication with id: {} updated successfully.", medicationDto.getId());
        return medicationRepository.save(medication);
    }

    public void deleteMedicationById(Integer id){
        Optional<Medication> medication = medicationRepository.findById(id);
        if(medication.isEmpty()){
            logger.error("Medication with id: {} does not exist!", id);
            throw new SecurityException("Medication does not exist!");
        }
        logger.info("Medication deleted successfully.");
        medicationRepository.delete(medication.get());
    }

    public Medication getMedicationById(Integer id){
        Optional<Medication> medication = medicationRepository.findById(id);
        if(medication.isEmpty()){
            logger.error("Medication with id: {} does not exist!", id);
            throw new SecurityException("Medication does not exist!");
        }
        logger.info("Medication retrieved successfully.");
        return medication.get();
    }

    public List<Medication> getMedicationByPatientId(Integer id){
        List<Medication> medications= medicationRepository.findByPatientId(id);
        if(medications.isEmpty()){
            logger.error("Medication with id: {} does not exist!", id);
            throw new SecurityException("Medication does not exist!");
        }
        logger.info("Medication retrieved successfully.");
        return medications;
    }

    public List<Medication> medicationList(Pageable pageable){
        logger.info("List of medication retrieved successfully.");
        return medicationRepository.findAll(pageable);
    }

}
