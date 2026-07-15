package com.project.project.Repo;

import com.project.project.Entity.Appointment;
import com.project.project.Entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppointmentRepo extends JpaRepository<Appointment,Long> {
    Optional<Appointment> findByPatient(Patient patient);
}
