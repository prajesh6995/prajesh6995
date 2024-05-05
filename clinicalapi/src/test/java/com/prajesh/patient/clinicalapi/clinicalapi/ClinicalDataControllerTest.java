package com.prajesh.patient.clinicalapi.clinicalapi;

import com.prajesh.patient.clinicalapi.clinicalapi.controller.ClinicalDataController;
import com.prajesh.patient.clinicalapi.clinicalapi.dto.ClinicalRequest;
import com.prajesh.patient.clinicalapi.clinicalapi.models.ClinicalData;
import com.prajesh.patient.clinicalapi.clinicalapi.models.Patient;
import com.prajesh.patient.clinicalapi.clinicalapi.Repo.ClinicalDataRepo;
import com.prajesh.patient.clinicalapi.clinicalapi.Repo.PatientRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClinicalDataControllerTest {

    @InjectMocks
    ClinicalDataController clinicalDataController;

    @Mock
    ClinicalDataRepo clinicalDataRepo;

    @Mock
    PatientRepo patientRepo;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return all clinical data")
    public void getAllClinicalDataTest() {
        when(clinicalDataRepo.findAll()).thenReturn(Arrays.asList(new ClinicalData(), new ClinicalData()));
        List<ClinicalData> clinicalData = clinicalDataController.getAllClinicalData();
        assertEquals(2, clinicalData.size());
    }

    @Test
    @DisplayName("Should return clinical data by id")
    public void getClinicalDataByIdTest() {
        ClinicalData clinicalData = new ClinicalData();
        clinicalData.setId(1);
        when(clinicalDataRepo.findById(1)).thenReturn(Optional.of(clinicalData));
        ClinicalData result = clinicalDataController.getClinicalDataById(1);
        assertEquals(1, result.getId());
    }

    @Test
    @DisplayName("Should return null when clinical data not found by id")
    public void getClinicalDataByIdNotFoundTest() {
        when(clinicalDataRepo.findById(1)).thenReturn(Optional.empty());
        ClinicalData result = clinicalDataController.getClinicalDataById(1);
        assertNull(result);
    }

    @Test
    @DisplayName("Should create a new clinical data")
    public void createClinicalDataTest() {
        ClinicalData clinicalData = new ClinicalData();
        clinicalData.setComponentName("Blood Pressure");
        clinicalData.setComponentValue("120/80");
        when(clinicalDataRepo.save(any(ClinicalData.class))).thenReturn(clinicalData);
        ClinicalData result = clinicalDataController.createClinicalData(clinicalData);
        assertEquals("Blood Pressure", result.getComponentName());
        assertEquals("120/80", result.getComponentValue());
    }

    @Test
    @DisplayName("Should update an existing clinical data")
    public void updateClinicalDataTest() {
        ClinicalData clinicalData = new ClinicalData();
        clinicalData.setId(1);
        clinicalData.setComponentName("Blood Pressure");
        clinicalData.setComponentValue("120/80");
        when(clinicalDataRepo.findById(1)).thenReturn(Optional.of(clinicalData));
        when(clinicalDataRepo.save(any(ClinicalData.class))).thenReturn(clinicalData);
        ClinicalData updatedClinicalData = new ClinicalData();
        updatedClinicalData.setComponentName("Blood Pressure");
        updatedClinicalData.setComponentValue("130/85");
        ClinicalData result = clinicalDataController.updateClinicalData(1, updatedClinicalData);
        assertEquals("Blood Pressure", result.getComponentName());
        assertEquals("130/85", result.getComponentValue());
    }

    @Test
    @DisplayName("Should return null when updating non-existing clinical data")
    public void updateNonExistingClinicalDataTest() {
        when(clinicalDataRepo.findById(1)).thenReturn(Optional.empty());
        ClinicalData updatedClinicalData = new ClinicalData();
        updatedClinicalData.setComponentName("Blood Pressure");
        updatedClinicalData.setComponentValue("130/85");
        ClinicalData result = clinicalDataController.updateClinicalData(1, updatedClinicalData);
        assertNull(result);
    }

    @Test
    @DisplayName("Should delete a clinical data by id")
    public void deleteClinicalDataTest() {
        doNothing().when(clinicalDataRepo).deleteById(1);
        clinicalDataController.deleteClinicalData(1);
        verify(clinicalDataRepo, times(1)).deleteById(1);
    }

    @Test
    @DisplayName("Should create a new clinical data with patient")
    public void createClinicalDataWithPatientTest() {
        Patient patient = new Patient();
        patient.setId(1);
        when(patientRepo.findById(1)).thenReturn(Optional.of(patient));
        ClinicalRequest clinicalRequest = new ClinicalRequest();
        clinicalRequest.setComponentName("Blood Pressure");
        clinicalRequest.setComponentValue("120/80");
        clinicalRequest.setPatientId(1);
        ClinicalData clinicalData = new ClinicalData();
        clinicalData.setComponentName(clinicalRequest.getComponentName());
        clinicalData.setComponentValue(clinicalRequest.getComponentValue());
        clinicalData.setPatient(patient);
        when(clinicalDataRepo.save(any(ClinicalData.class))).thenReturn(clinicalData);
        ClinicalData result = clinicalDataController.createClinicalData(clinicalRequest);
        assertEquals("Blood Pressure", result.getComponentName());
        assertEquals("120/80", result.getComponentValue());
        assertEquals(1, result.getPatient().getId());
    }

    @Test
    @DisplayName("Should throw exception when creating clinical data with non-existing patient")
    public void createClinicalDataWithNonExistingPatientTest() {
        when(patientRepo.findById(1)).thenReturn(Optional.empty());
        ClinicalRequest clinicalRequest = new ClinicalRequest();
        clinicalRequest.setComponentName("Blood Pressure");
        clinicalRequest.setComponentValue("120/80");
        clinicalRequest.setPatientId(1);
        assertThrows(ResponseStatusException.class, () -> clinicalDataController.createClinicalData(clinicalRequest));
    }
}
