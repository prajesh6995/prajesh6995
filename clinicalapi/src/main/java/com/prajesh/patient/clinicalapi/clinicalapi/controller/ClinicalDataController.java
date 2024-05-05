package com.prajesh.patient.clinicalapi.clinicalapi.controller;

import com.prajesh.patient.clinicalapi.clinicalapi.Repo.PatientRepo;
import com.prajesh.patient.clinicalapi.clinicalapi.dto.ClinicalRequest;
import com.prajesh.patient.clinicalapi.clinicalapi.models.ClinicalData;
import com.prajesh.patient.clinicalapi.clinicalapi.Repo.ClinicalDataRepo;
import com.prajesh.patient.clinicalapi.clinicalapi.models.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/clinicaldata")
@CrossOrigin(origins = "*")
public class ClinicalDataController {

    @Autowired
    private ClinicalDataRepo clinicalDataRepo;

    @Autowired
    private PatientRepo patientRepo;

    @GetMapping
    public List<ClinicalData> getAllClinicalData() {
        return clinicalDataRepo.findAll();
    }

    @GetMapping("/{id}")
    public ClinicalData getClinicalDataById(@PathVariable Integer id) {
        return clinicalDataRepo.findById(id).orElse(null);
    }

    @PostMapping
    public ClinicalData createClinicalData(@RequestBody ClinicalData clinicalData) {
        return clinicalDataRepo.save(clinicalData);
    }

    @PutMapping("/{id}")
    public ClinicalData updateClinicalData(@PathVariable Integer id, @RequestBody ClinicalData clinicalDataDetails) {
        ClinicalData clinicalData = clinicalDataRepo.findById(id).orElse(null);
        if (clinicalData != null) {
            // update the fields of clinicalData based on clinicalDataDetails
            // assuming clinicalData has fields like 'componentName', 'componentValue'
            clinicalData.setComponentName(clinicalDataDetails.getComponentName());
            clinicalData.setComponentValue(clinicalDataDetails.getComponentValue());
            return clinicalDataRepo.save(clinicalData);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteClinicalData(@PathVariable Integer id) {
        clinicalDataRepo.deleteById(id);
    }

    @PostMapping("/{clinicals}")
    public ClinicalData createClinicalData(@RequestBody ClinicalRequest clinicalRequest) {
        Patient patient = patientRepo.findById(clinicalRequest.getPatientId()).orElse(null);
        if (patient == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found");
        }

        ClinicalData clinicalData = new ClinicalData();
        clinicalData.setComponentName(clinicalRequest.getComponentName());
        clinicalData.setComponentValue(clinicalRequest.getComponentValue());
        clinicalData.setPatient(patient);

        return clinicalDataRepo.save(clinicalData);
    }


}