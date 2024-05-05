package com.prajesh.patient.clinicalapi.clinicalapi.Repo;

import com.prajesh.patient.clinicalapi.clinicalapi.models.ClinicalData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClinicalDataRepo extends JpaRepository<ClinicalData, Integer> {

}
