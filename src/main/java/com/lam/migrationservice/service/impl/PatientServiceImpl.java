package com.lam.migrationservice.service.impl;

import com.lam.migrationservice.model.Patient;
import com.lam.migrationservice.model.PatientStatus;
import com.lam.migrationservice.repository.PatientRepository;
import com.lam.migrationservice.service.PatientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public List<Patient> findAllActivePatients() {
        return patientRepository.findAllOldGuidsByStatus(PatientStatus.ACTIVE);
    }
}
