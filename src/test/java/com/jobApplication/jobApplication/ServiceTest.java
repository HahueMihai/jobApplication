package com.jobApplication.jobApplication;

import com.jobApplication.jobApplication.controller.MedicationController;
import com.jobApplication.jobApplication.model.Medication;
import com.jobApplication.jobApplication.model.MedicationDto;
import com.jobApplication.jobApplication.service.MedicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ServiceTest {
    @InjectMocks
    private MedicationController medicationController;

    @Mock
    private MedicationService medicationService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(medicationController).build();
    }

    @Test
    public void testSaveMedication() throws Exception {
        Medication medication = new Medication();
        when(medicationService.saveMedication(any(MedicationDto.class))).thenReturn(medication);

        mockMvc.perform(post("/api/medication")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Test Medication\", \"dosage\": \"20mg\"}"))
                .andExpect(status().isOk());

        verify(medicationService, times(1)).saveMedication(any(MedicationDto.class));
    }

    @Test
    public void testUpdateMedication() throws Exception {
        Medication medication = new Medication();
        when(medicationService.updateMedication(any(Medication.class))).thenReturn(medication);

        mockMvc.perform(put("/api/medication")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"name\": \"Updated Medication\", \"dosage\": \"25mg\"}"))
                .andExpect(status().isOk());

        verify(medicationService, times(1)).updateMedication(any(Medication.class));
    }

    @Test
    public void testDeleteById() throws Exception {
        doNothing().when(medicationService).deleteMedicationById(anyInt());

        mockMvc.perform(delete("/api/medication/1"))
                .andExpect(status().isOk());

        verify(medicationService, times(1)).deleteMedicationById(anyInt());
    }

    @Test
    public void testFindAllMedications() throws Exception {
        Medication medication = new Medication();
        List<Medication> medications = Arrays.asList(medication);
        when(medicationService.medicationList(any(Pageable.class))).thenReturn(medications);

        mockMvc.perform(get("/api/medication/findAll")
                        .param("pageNumber", "0")
                        .param("pageSize", "10")
                        .param("sortBy", "id")
                        .param("sortDirection", "desc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        verify(medicationService, times(1)).medicationList(any(Pageable.class));
    }


}
