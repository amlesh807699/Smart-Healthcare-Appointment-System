package com.project.project.Repo;

import com.project.project.Entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Doctorrepo extends JpaRepository<Doctor,Long> {
    List<Doctor> findByNameAndSpecializationAndCity(
            String name,
            String specialization,
            String city
    );
}
