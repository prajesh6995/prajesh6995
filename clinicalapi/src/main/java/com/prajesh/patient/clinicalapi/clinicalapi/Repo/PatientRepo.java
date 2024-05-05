package com.prajesh.patient.clinicalapi.clinicalapi.Repo;

import com.prajesh.patient.clinicalapi.clinicalapi.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepo extends JpaRepository<Patient, Integer> {
}
