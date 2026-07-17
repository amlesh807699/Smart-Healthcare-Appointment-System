package com.project.project.Repo;

import com.project.project.Entity.Patient;
import com.project.project.Entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReportRepo extends JpaRepository<Report,Long> {


        List<Report> findByPatient(Patient patient);

        // ya
        List<Report> findByPatientId(Long patientId);
}
