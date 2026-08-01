package com.project.project.Serivce;

import com.project.project.CloudNairy.CloudinaryService;
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
import org.springframework.web.multipart.MultipartFile;

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
    private final CloudinaryService cloudinaryService;

    public DoctorResDto addProfile(DoctorReqDto doctorReqDto, MultipartFile profilepic) {

        // Step 1: DTO check
        System.out.println("1. DTO DATA: " + doctorReqDto);


        User user = userSerivce.getCurrentUser();

        // Step 2: Current user check
        System.out.println("2. CURRENT USER: " + user);


        if (user.getRole() != Role.DOCTOR) {
            throw new RuntimeException("Access Denied");
        }


        if (doctorrepo.findByUser(user).isPresent()) {
            throw new RuntimeException("Doctor profile already exists");
        }


        // Step 3: DTO to Entity mapping check
        Doctor doctor = doctorMapping.toEntity(doctorReqDto);

        System.out.println("3. ENTITY AFTER MAPPING: " + doctor);


        // Step 4: Image upload check
        String img = cloudinaryService.uploadImage(profilepic);

        System.out.println("4. CLOUDINARY IMAGE URL: " + img);


        doctor.setProfilepic(img);

        // Step 5: After profile pic set
        System.out.println("5. AFTER PROFILE PIC: " + doctor);


        doctor.setUser(user);

        // Step 6: After user set
        System.out.println("6. AFTER USER SET: " + doctor);


        user.setProfileCompleted(true);
        userRepo.save(user);


        // Step 7: Before save database
        System.out.println("7. BEFORE DATABASE SAVE: " + doctor);


        Doctor saved = doctorrepo.save(doctor);


        // Step 8: After save
        System.out.println("8. SAVED DOCTOR: " + saved);


        DoctorResDto response = doctorMapping.toDto(saved);

        // Step 9: Final response
        System.out.println("9. RESPONSE DTO: " + response);


        return response;
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

    public String deleteReport(Long id) {
           User user=userSerivce.getCurrentUser();
        Report report = reportRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Report not found"));

        reportRepo.delete(report);
        return "report delted";
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
