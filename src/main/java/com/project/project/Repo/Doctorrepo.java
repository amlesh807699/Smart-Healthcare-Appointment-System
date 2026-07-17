package com.project.project.Repo;

import com.project.project.Entity.Doctor;
import com.project.project.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface Doctorrepo extends JpaRepository<Doctor,Long> {
    List<Doctor> findByNameAndSpecializationAndCity(
            String name,
            String specialization,
            String city
    );

    Optional<Doctor> findByUser(User user);
}
