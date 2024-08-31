package com.lam.migrationservice.service;

import com.lam.migrationservice.model.Patient;

import java.util.List;

public interface PatientService {
    List<Patient> findAllActivePatients();
}
