package com.project.project.Serivce;

import com.project.project.Dto.Appointment.AppointmentMapper;
import com.project.project.Dto.Appointment.AppointmentReqDto;
import com.project.project.Dto.Appointment.AppointmentResDto;
import com.project.project.Dto.Doctor.DoctorMapping;
import com.project.project.Dto.Doctor.DoctorResDto;
import com.project.project.Dto.Patient.PatientMapping;
import com.project.project.Dto.Patient.PatientReqDto;
import com.project.project.Dto.Patient.PatientResDto;
import com.project.project.Dto.Report.ReportMapper;
import com.project.project.Dto.Report.ReportResDto;
import com.project.project.Entity.*;
import com.project.project.Repo.*;
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
    private final ReportMapper reportMapper;
    private final ReportRepo reportRepo;
    private final AppointmentStatus appointmentStatus;

    public PatientResDto addProfile(PatientReqDto dto){

        User user=userSerivce.getCurrentUser();

        Patient patient=patientMapping.toEntity(dto);

        patient.setUser(user);

        Patient saved=patientRepo.save(patient);

        return patientMapping.toDto(saved);
    }

//    private PatientResDto updateprofile(Long id,PatientReqDto patientReqDto){


    private List<DoctorResDto> searchDoctor (String name,String specialization,String city){
        List<Doctor> doctorList=doctorrepo.findByNameAndSpecializationAndCity(name,specialization,city);
        return doctorMapping.toDtoList(doctorList);
    }

    private DoctorResDto searchbyid(Long id){
        Doctor doctor=doctorrepo.findById(id).orElse(null);
        return doctorMapping.toDto(doctor);
    }
    private List<DoctorResDto> all(){
        List<Doctor> doctor=doctorrepo.findAll();
        return doctorMapping.toDtoList(doctor);
    }

    public AppointmentResDto book(AppointmentReqDto dto){

        User user=userSerivce.getCurrentUser();

        Patient patient=patientRepo.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        Doctor doctor=doctorrepo.findById(dto.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        Appointment appointment=appointmentMapper.toEntity(dto);

        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setStatus(AppointmentStatus.BOOKED);

        Appointment saved=appointmentRepo.save(appointment);

        return appointmentMapper.toDto(saved);

    }

    public List<AppointmentResDto> getAllAppointments() {

        User user = userSerivce.getCurrentUser();

        Patient patient = patientRepo.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        List<Appointment> appointments = appointmentRepo.findByPatient(patient);

        return appointmentMapper.toDto(appointments);
    }

    public AppointmentResDto getAppointmentById(Long id) {

        User user = userSerivce.getCurrentUser();

        Patient patient = patientRepo.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        Appointment appointment = appointmentRepo.findByIdAndPatient(id, patient)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        return appointmentMapper.toDto(appointment);
    }

    private String dlete(Long id){
        Appointment appointment=appointmentRepo.findById(id).orElse(null);
        appointmentRepo.delete(appointment);
        return "apPoinmentes delted";
    }

    private List<ReportResDto> getAllReports() {

        User user = userSerivce.getCurrentUser();

        Patient patient = patientRepo.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        List<Report> reports = reportRepo.findByPatient(patient);

        return reportMapper.toDto(reports);
    }

    private ReportResDto byidss(Long id){
        Report report=reportRepo.findById(id).orElse(null);
        return reportMapper.ToDto(report);
    }








}
