package com.jobApplication.jobApplication.controller;

import com.itextpdf.text.DocumentException;
import com.jobApplication.jobApplication.model.Medication;
import com.jobApplication.jobApplication.model.Patient;
import com.jobApplication.jobApplication.service.PatientService;
import com.jobApplication.jobApplication.service.PdfService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@RestController
@Tag(name="Patients")
@RequestMapping("/api/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private PdfService pdfService;

    @Operation(
            summary = "Save patients to db",
            method = "POST",
            description = "This endpoint allows saving a patients to the database."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Medication saved successfully."),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request")
            })
    @PostMapping
    public Patient savePatient(@RequestBody Patient patient){
        return patientService.savePatient(patient);
    }


    @Operation(
            summary = "Find all patients by medication name and send notifications",
            method = "GET",
            description = "This endpoint finds all patients taking a specified medication and sends email notifications to them."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Emails have been sent to all patients prescribed with the medication."),
                    @ApiResponse(responseCode = "404", description = "No patients found for the given medication name."),
                    @ApiResponse(responseCode = "500", description = "An error occurred while sending notifications.")
            }
    )
    @RequestMapping(value = "/getPatientsByMedName/{medicationName}", method = RequestMethod.GET)
    public List<Patient> findAllPatientsByMedName(@PathVariable(value = "medicationName") String medicationName,
                                                  @RequestParam(defaultValue = "0") Integer pageNumber,
                                                  @RequestParam(defaultValue = "10") Integer pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return patientService.findAllPatientsByMedName(medicationName, pageable);
    }

    @GetMapping("/{id}/report")
    public ResponseEntity<byte[]> generateReport(
            @PathVariable(value = "id") Integer id) throws DocumentException, IOException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.add("Content-Disposition", "inline; filename=report.pdf");
        return new ResponseEntity<>(
                pdfService.generateMedicationSchedulePdf(id), headers, HttpStatus.OK);
    }
}
