package com.project.project.Repo;

import com.project.project.Entity.Appointment;
import com.project.project.Entity.Doctor;
import com.project.project.Entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AppointmentRepo extends JpaRepository<Appointment, Long> {

    List<Appointment> findByPatient(Patient patient);

    Optional<Appointment> findByIdAndPatient(Long id, Patient patient);

    List<Appointment> findByDoctor(Doctor doctor);

    Optional<Appointment> findByIdAndDoctor(Long id,Doctor doctor);
}