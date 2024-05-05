package com.prajesh.patient.clinicalapi.clinicalapi;

import com.prajesh.patient.clinicalapi.clinicalapi.controller.PatientController;
import com.prajesh.patient.clinicalapi.clinicalapi.models.Patient;
import com.prajesh.patient.clinicalapi.clinicalapi.Repo.PatientRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PatientControllerTest {

    @InjectMocks
    PatientController patientController;

    @Mock
    PatientRepo patientRepo;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return all patients")
    public void getAllPatientsTest() {
        when(patientRepo.findAll()).thenReturn(Arrays.asList(new Patient(), new Patient()));
        List<Patient> patients = patientController.getAllPatients();
        assertEquals(2, patients.size());
    }

    @Test
    @DisplayName("Should return patient by id")
    public void getPatientByIdTest() {
        Patient patient = new Patient();
        patient.setId(1);
        when(patientRepo.findById(1)).thenReturn(Optional.of(patient));
        Patient result = patientController.getPatientById(1);
        assertEquals(1, result.getId());
    }

    @Test
    @DisplayName("Should return null when patient not found by id")
    public void getPatientByIdNotFoundTest() {
        when(patientRepo.findById(1)).thenReturn(Optional.empty());
        Patient result = patientController.getPatientById(1);
        assertNull(result);
    }

    @Test
    @DisplayName("Should create a new patient")
    public void createPatientTest() {
        Patient patient = new Patient();
        patient.setFirstName("John");
        patient.setLastName("Doe");
        when(patientRepo.save(any(Patient.class))).thenReturn(patient);
        Patient result = patientController.createPatient(patient);
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
    }

    @Test
    @DisplayName("Should update an existing patient")
    public void updatePatientTest() {
        Patient patient = new Patient();
        patient.setId(1);
        patient.setFirstName("John");
        patient.setLastName("Doe");
        when(patientRepo.findById(1)).thenReturn(Optional.of(patient));
        when(patientRepo.save(any(Patient.class))).thenReturn(patient);
        Patient updatedPatient = new Patient();
        updatedPatient.setFirstName("Jane");
        updatedPatient.setLastName("Doe");
        Patient result = patientController.updatePatient(1, updatedPatient);
        assertEquals("Jane", result.getFirstName());
        assertEquals("Doe", result.getLastName());
    }

    @Test
    @DisplayName("Should return null when updating non-existing patient")
    public void updateNonExistingPatientTest() {
        when(patientRepo.findById(1)).thenReturn(Optional.empty());
        Patient updatedPatient = new Patient();
        updatedPatient.setFirstName("Jane");
        updatedPatient.setLastName("Doe");
        Patient result = patientController.updatePatient(1, updatedPatient);
        assertNull(result);
    }

    @Test
    @DisplayName("Should delete a patient by id")
    public void deletePatientTest() {
        doNothing().when(patientRepo).deleteById(1);
        patientController.deletePatient(1);
        verify(patientRepo, times(1)).deleteById(1);
    }
}