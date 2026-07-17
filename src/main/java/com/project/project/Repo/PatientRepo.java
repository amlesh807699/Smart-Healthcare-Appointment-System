package com.project.project.Repo;

import com.project.project.Entity.Patient;
import com.project.project.Entity.Report;
import com.project.project.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PatientRepo extends JpaRepository<Patient,Long> {
    Optional<Patient> findByUser(User user);
    Optional<Patient> findByIdAndReport(Long id,Report report);
}
