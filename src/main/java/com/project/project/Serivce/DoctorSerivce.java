package com.project.project.Serivce;

import com.project.project.Dto.Appointment.AppoinmentStatusReqDto;
import com.project.project.Dto.Appointment.AppointmentMapper;
import com.project.project.Dto.Appointment.AppointmentResDto;
import com.project.project.Dto.Doctor.DoctorMapping;
import com.project.project.Dto.Doctor.DoctorReqDto;
import com.project.project.Dto.Doctor.DoctorResDto;
import com.project.project.Dto.Patient.PatientMapping;
import com.project.project.Dto.Patient.PatientResDto;
import com.project.project.Dto.Report.ReportMapper;
import com.project.project.Dto.Report.ReportReqDto;
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
public class DoctorSerivce {
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


    public DoctorResDto addProfile(DoctorReqDto doctorReqDto) {

        User user = userSerivce.getCurrentUser();

        if (user.getRole() != Role.DOCTOR) {
            throw new RuntimeException("Access Denied");
        }

        if (doctorrepo.findByUser(user).isPresent()) {
            throw new RuntimeException("Doctor profile already exists");
        }

        Doctor doctor = doctorMapping.toEntity(doctorReqDto);

        doctor.setUser(user);

        Doctor saved = doctorrepo.save(doctor);

        return doctorMapping.toDto(saved);
    }


    public DoctorResDto getProfile() {

        User user = userSerivce.getCurrentUser();

        Doctor doctor = doctorrepo.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Doctor profile not found"));

        return doctorMapping.toDto(doctor);
    }

    public List<AppointmentResDto> getDoctorAppointments() {

        User user = userSerivce.getCurrentUser();

        Doctor doctor = doctorrepo.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Doctor profile not found"));

        List<Appointment> appointments = appointmentRepo.findByDoctor(doctor);

        return appointmentMapper.toDto(appointments);
    }

    public AppointmentResDto acceptappoinmet(Long id, AppoinmentStatusReqDto appoinmentStatusReqDto){
        User user=userSerivce.getCurrentUser();

        Doctor doctor = doctorrepo.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Doctor profile not found"));

        Appointment appointment = appointmentRepo
                .findByIdAndDoctor(id, doctor)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        if (appointment.getStatus() == AppointmentStatus.COMPLETED) {
            throw new RuntimeException("Completed appointment cannot be updated");
        }

        // Already cancelled
        if (appointment.getStatus() == AppointmentStatus.CANCELLED) {
            throw new RuntimeException("Cancelled appointment cannot be updated");
        }


        appointment.setStatus(appoinmentStatusReqDto.getStatus());

        Appointment saved = appointmentRepo.save(appointment);

        return appointmentMapper.toDto(saved);

    }

    public ReportResDto addReport(Long patientId, ReportReqDto reportReqDto) {

        User user = userSerivce.getCurrentUser();

        Doctor doctor = doctorrepo.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        Patient patient = patientRepo.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        Report report = reportMapper.ToEntity(reportReqDto);

        report.setPatient(patient);

        report.setDoctor(doctor);

        Report saved = reportRepo.save(report);

        return reportMapper.ToDto(saved);
    }

    public void deleteReport(Long id) {

        Report report = reportRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Report not found"));

        reportRepo.delete(report);

    }

    public List<ReportResDto> patientReport(Long id) {

        Patient patient = patientRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        List<Report> reports = reportRepo.findByPatient(patient);

        return reportMapper.toDto(reports);
    }

    public ReportResDto reportbyid(Long id){
        Report report = reportRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Report not found"));
        return reportMapper.ToDto(report);
    }

    public List<PatientResDto> getAllPatients() {

        User user = userSerivce.getCurrentUser();

        Doctor doctor = doctorrepo.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        List<Appointment> appointments = appointmentRepo.findByDoctor(doctor);

        List<Patient> patients = appointments.stream()
                .map(Appointment::getPatient)
                .distinct()
                .toList();

        return patientMapping.tolistdto(patients);
    }

    public PatientResDto patientResDto(Long id){
        Patient patient=patientRepo.findById(id).orElse(null);
        return patientMapping.toDto(patient);
    }

}
