package com.project.project.Serivce;

import com.project.project.Dto.Appointment.AppointmentMapper;
import com.project.project.Dto.Appointment.AppointmentReqDto;
import com.project.project.Dto.Appointment.AppointmentResDto;
import com.project.project.Dto.Doctor.DoctorMapping;
import com.project.project.Dto.Doctor.DoctorResDto;
import com.project.project.Dto.Patient.PatientMapping;
import com.project.project.Dto.Patient.PatientReqDto;
import com.project.project.Dto.Patient.PatientResDto;
import com.project.project.Entity.Appointment;
import com.project.project.Entity.Doctor;
import com.project.project.Entity.Patient;
import com.project.project.Entity.User;
import com.project.project.Repo.AppointmentRepo;
import com.project.project.Repo.Doctorrepo;
import com.project.project.Repo.PatientRepo;
import com.project.project.Repo.UserRepo;
import com.project.project.UserSerivce.UserSerivce;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientSerivce {

    private final PatientRepo patientRepo;
    private final Doctorrepo doctorrepo;
    private final DoctorMapping doctorMapping;
    private final PatientMapping patientMapping;
    private final AppointmentRepo appointmentRepo;
    private final AppointmentMapper appointmentMapper;
    private final UserSerivce userSerivce;
    private final UserRepo userRepo;

    private PatientResDto profile(PatientReqDto patientReqDto){
        Patient patient=patientMapping.ToEntity(patientReqDto);
         Patient saved=patientRepo.save(patient);
         return patientMapping.toDto(saved);
    }

//    private PatientResDto updateprofile(Long id,PatientReqDto patientReqDto){


    private List<DoctorResDto> serach (String name,String specialization,String city){
        List<Doctor> doctorList=doctorrepo.findByNameAndSpecializationAndCity(name,specialization,city);
        return doctorMapping.DTO_LIST(doctorList);
    }

    private DoctorResDto byid(Long id){
        Doctor doctor=doctorrepo.findById(id).orElse(null);
        return doctorMapping.toDto(doctor);
    }
    private List<DoctorResDto> all(){
        List<Doctor> doctor=doctorrepo.findAll();
        return doctorMapping.DTO_LIST(doctor);
    }

    private AppointmentResDto book (AppointmentReqDto appointmentReqDto){
        Appointment appointment=appointmentMapper.ToEntity(appointmentReqDto);
        Appointment saved=appointmentRepo.save(appointment);
        return appointmentMapper.ToDto(saved);
    }

    private AppointmentResDto allap(){
        User user=userSerivce.getCurrentUser();
        Patient patient=patientRepo.findByUser(user).orElse(null);
        Appointment appointment=appointmentRepo.findByPatient(patient).orElse(null);
        return appointmentMapper.ToDto(appointment);
    }

    private AppointmentResDto byids(Long id){
        Appointment appointment=appointmentRepo.findById(id).orElse(null);
        return appointmentMapper.ToDto(appointment);
    }

    private String dlete(Long id){
        Appointment appointment=appointmentRepo.findById(id).orElse(null);
        return "apPoinmentes delted";
    }







}
