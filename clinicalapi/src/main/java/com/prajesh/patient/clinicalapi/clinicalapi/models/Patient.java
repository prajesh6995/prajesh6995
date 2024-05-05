package com.prajesh.patient.clinicalapi.clinicalapi.models;
// Generate the jpa model class for patient which contain firstname, lastname and age
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String lastName;
    private int age;
    // List of Clinical Data for one to many relationship

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patient", fetch = FetchType.EAGER)
    private List<ClinicalData> clinicalData;



    public Patient() {
    }

    public Patient(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    public List<ClinicalData> getClinicalData() {
        return clinicalData;
    }

    public void setClinicalData(List<ClinicalData> clinicalData) {
        this.clinicalData = clinicalData;
    }
}