package com.jobApplication.jobApplication.service;

import com.jobApplication.jobApplication.model.MailStructure;
import com.jobApplication.jobApplication.model.Medication;
import com.jobApplication.jobApplication.model.Patient;
import com.jobApplication.jobApplication.repository.PatientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private MailSenderService mailSenderService;

    private final Logger logger = LoggerFactory.getLogger(PatientService.class);

    public Patient savePatient(Patient patient) {
        logger.info("Patient saved successfully.");
        return patientRepository.save(patient);
    }

    public List<Patient> findAllPatientsByMedName(String medName, Pageable pageable){
        List<Patient> patientList = patientRepository.findAllPatientsByMedName(medName, pageable);

        for(Patient patient : patientList){
            if(patient.getEmail()!=null){
                MailStructure mailStructure = MailStructure.builder()
                        .subject("Hello patient: " + patient.getName())
                        .message("Dont forget to take your medication: "+ medName)
                        .buttonText("Take!")
                        .buttonUrl("http://localhost:8080/mail/take/"+patient.getEmail())
                        .build();
                mailSenderService.sendMail(patient.getEmail(), mailStructure);
            }
        }
        logger.info("Sending notifications to all patients prescribed with the medication: {}", medName);
        return patientList;
    }

    public Patient findById(Integer patientId) {
        return patientRepository.findById(patientId).orElse(null);
    }
}
