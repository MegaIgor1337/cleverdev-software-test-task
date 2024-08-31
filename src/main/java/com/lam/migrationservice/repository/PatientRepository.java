package com.lam.migrationservice.repository;

import com.lam.migrationservice.model.Patient;
import com.lam.migrationservice.model.PatientStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    @Query("SELECT p FROM Patient as p " +
            "WHERE p.status = :status ")
    List<Patient> findAllOldGuidsByStatus(@Param("status")PatientStatus status);
}
