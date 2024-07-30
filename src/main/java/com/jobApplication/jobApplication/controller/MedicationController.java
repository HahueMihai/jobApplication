package com.jobApplication.jobApplication.controller;

import com.jobApplication.jobApplication.model.Medication;
import com.jobApplication.jobApplication.model.MedicationDto;
import com.jobApplication.jobApplication.service.MedicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name="Medication")
@RequestMapping("/api/medication")
public class MedicationController {

    @Autowired
    private MedicationService medicationService;

    @Operation(
            summary = "Save medication to db",
            method = "POST",
            description = "This endpoint allows saving a medication to the database."
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
    public Medication saveMedication(@RequestBody MedicationDto medication){
       return medicationService.saveMedication(medication);
    }

    @Operation(
            summary = "Update medication in db",
            method = "POST",
            description = "This endpoint allows updating an existing medication in the database."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Medication updated successfully."),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Medication does not exist!")
            })
    @PutMapping
    public Medication updateMedication(@RequestBody Medication medication){
        return medicationService.updateMedication(medication);
    }

    @Operation(
            summary = "Delete medication by ID",
            method = "DELETE",
            description = "This endpoint allows deleting a medication by its ID."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Medication deleted successfully."),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Medication does not exist!")
            })
    @DeleteMapping(value = "/{id}")
    public void deleteById(@PathVariable(value = "id") Integer id){
        medicationService.deleteMedicationById(id);
    }

    @Operation(
            summary = "List medication schedules",
            method = "GET",
            description = "This endpoint provides a list of medication schedules with sort options."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of medication retrieved successfully."),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request")
            })
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public List<Medication> findAllMedications(@RequestParam(defaultValue = "0") Integer pageNumber,
                                               @RequestParam(defaultValue = "10") Integer pageSize,
                                               @RequestParam(defaultValue = "id") String sortBy,
                                               @RequestParam(defaultValue = "desc") String sortDirection){
        Sort.Direction direction =
                Sort.Direction.fromOptionalString(sortDirection).orElse(Sort.Direction.DESC);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, direction, sortBy);
        return medicationService.medicationList(pageable);
    }

    @Operation(
            summary = "Find medication by ID",
            method = "GET",
            description = "This endpoint retrieves a medication by its ID."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Medication retrieved successfully."),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "404", description = "Medication does not exist!")
            })
    @GetMapping(value = "/{id}")
    public Medication findMedsById(@PathVariable(value = "id") Integer id){
        return medicationService.getMedicationById(id);
    }

}
