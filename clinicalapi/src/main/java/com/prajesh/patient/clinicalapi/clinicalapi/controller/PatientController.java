package com.prajesh.patient.clinicalapi.clinicalapi.controller;

import com.prajesh.patient.clinicalapi.clinicalapi.models.Patient;
import com.prajesh.patient.clinicalapi.clinicalapi.Repo.PatientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
@CrossOrigin(origins = "*")
public class PatientController {

    @Autowired
    private PatientRepo patientRepo;

    private static final Logger logger = LoggerFactory.getLogger(PatientController.class);

    @GetMapping
    public List<Patient> getAllPatients() {
        logger.info("Getting all patients");
        return patientRepo.findAll();
    }

    @GetMapping("/{id}")
    public Patient getPatientById(@PathVariable Integer id) {
        logger.info("Getting patient with id: {}", id);
        return patientRepo.findById(id).orElse(null);
    }

    @PostMapping
    public Patient createPatient(@RequestBody Patient patient) {
        logger.info("Creating patient: {}", patient);
        return patientRepo.save(patient);
    }

    @PutMapping("/{id}")
    public Patient updatePatient(@PathVariable Integer id, @RequestBody Patient patientDetails) {
        logger.info("Updating patient with id: {}", id);
        Patient patient = patientRepo.findById(id).orElse(null);
        if (patient != null) {
            patient.setFirstName(patientDetails.getFirstName());
            patient.setLastName(patientDetails.getLastName());
            patient.setAge(patientDetails.getAge());
            return patientRepo.save(patient);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deletePatient(@PathVariable Integer id) {
        logger.info("Deleting patient with id: {}", id);
        patientRepo.deleteById(id);
    }
}