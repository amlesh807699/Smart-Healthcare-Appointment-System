package com.project.project.Controller;

import com.project.project.Dto.Appointment.AppoinmentStatusReqDto;
import com.project.project.Dto.Appointment.AppointmentResDto;
import com.project.project.Dto.Doctor.DoctorReqDto;
import com.project.project.Dto.Doctor.DoctorResDto;
import com.project.project.Dto.Patient.PatientResDto;
import com.project.project.Dto.Report.ReportReqDto;
import com.project.project.Dto.Report.ReportResDto;
import com.project.project.Serivce.DoctorSerivce;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/doctor")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorSerivce doctorSerivce;
    @PostMapping(value = "/add/profile" ,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DoctorResDto> addprofile( @Valid  @RequestBody DoctorReqDto doctorReqDto, @RequestPart(value = "profilepic" ,required = true) MultipartFile profilepic){
        DoctorResDto doctorResDto=doctorSerivce.addProfile(doctorReqDto, profilepic);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(doctorResDto);
    }

    @GetMapping("/profile")
    public ResponseEntity<DoctorResDto> getprofile(){
        DoctorResDto doctorResDto=doctorSerivce.getProfile();
        return ResponseEntity.status(HttpStatus.OK)
                .body(doctorResDto);
    }


    @GetMapping("/appoinment")
    public ResponseEntity<List<AppointmentResDto>> getdoctorappoin(){
        List<AppointmentResDto> appointmentResDto=doctorSerivce.getDoctorAppointments();
        return ResponseEntity.status(HttpStatus.OK).body(appointmentResDto);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<AppointmentResDto> updateAppointmentStatus(
            @PathVariable Long id,
            @Valid @RequestBody AppoinmentStatusReqDto appoinmentStatusReqDto) {

        AppointmentResDto appointmentResDto =
                doctorSerivce.acceptappoinmet(id, appoinmentStatusReqDto);

        return ResponseEntity.ok(appointmentResDto);
    }

    @PostMapping("/add/report/{id}")
    public ResponseEntity<ReportResDto> addreport(@Valid @PathVariable Long id,@RequestBody ReportReqDto reportReqDto){
        ReportResDto reportResDto=doctorSerivce.addReport(id,reportReqDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reportResDto);
    }

    @DeleteMapping("/report/delete/{id}")
    public ResponseEntity<String> DeleteResport(@PathVariable  Long id){
        String reportResDto=doctorSerivce.deleteReport(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body("done");
    }

    @GetMapping("/patient/{patientId}/reports")
    public ResponseEntity<List<ReportResDto>> getPatientReports(
            @PathVariable Long patientId) {

        List<ReportResDto> reports = doctorSerivce.patientReport(patientId);

        return ResponseEntity.ok(reports);
    }

    // Get report by report id
    @GetMapping("/report/{reportId}")
    public ResponseEntity<ReportResDto> getReportById(
            @PathVariable Long reportId) {

        ReportResDto report = doctorSerivce.reportbyid(reportId);

        return ResponseEntity.ok(report);
    }

    // Get all patients of logged-in doctor
    @GetMapping("/patients")
    public ResponseEntity<List<PatientResDto>> getAllPatients() {

        List<PatientResDto> patients = doctorSerivce.getAllPatients();

        return ResponseEntity.ok(patients);
    }

    // Get patient by id
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<PatientResDto> getPatientById(
            @PathVariable Long patientId) {

        PatientResDto patient = doctorSerivce.patientResDto(patientId);

        return ResponseEntity.ok(patient);
    }
}
