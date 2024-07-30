package com.jobApplication.jobApplication.controller;

import com.jobApplication.jobApplication.model.MailStructure;
import com.jobApplication.jobApplication.model.Patient;
import com.jobApplication.jobApplication.repository.PatientRepository;
import com.jobApplication.jobApplication.service.MailSenderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mail")
@Tag(name="Mail")
public class MailController {
    @Autowired
    private MailSenderService mailSenderService;

    @Autowired
    private PatientRepository patientRepository;

    @PostMapping("/send/{email}")
    public String sendMail(@PathVariable(value = "email") String email, @RequestBody MailStructure mailStructure){
        mailSenderService.sendMail(email, mailStructure);
        return "Successfully sent the mail";
    }

    @GetMapping("/take/{email}")
    public String takeMedication(@PathVariable(value = "email") String email){
        Patient patient = patientRepository.findByEmail(email);
        patient.setTakeMed(true);
        return "Patient " + patient.getName() + "has been reminded to take their medication.";
    }
}
