package com.project.project.Controller;


import com.project.project.Dto.Doctor.DoctorResDto;
import com.project.project.Dto.Patient.PatientReqDto;
import com.project.project.Dto.Patient.PatientResDto;
import com.project.project.Entity.User;
import com.project.project.Serivce.PatientSerivce;
import com.project.project.UserSerivce.UserSerivce;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.print.Doc;
import java.util.List;

@RestController
@RequestMapping("/patient")
@RequiredArgsConstructor
@Slf4j
public class PatientController {

    private final PatientSerivce patientSerivce;
    private final UserSerivce userSerivce;


    @PostMapping("/add/profile")
    public ResponseEntity<PatientResDto> addprofile(@RequestPart(value = "patient")PatientReqDto patientReqDto, @RequestPart(value = "profilepic",required = true) MultipartFile profilepic){
        PatientResDto patientResDto1=patientSerivce.addProfile(patientReqDto,profilepic);
        return ResponseEntity.status(HttpStatus.OK).body(patientResDto1);
    }
    @GetMapping("/get/profile")
   public ResponseEntity<PatientResDto> profile(){
        User user=userSerivce.getCurrentUser();
        log.info(user.getEmail());
        PatientResDto patientResDto=patientSerivce.getMyProfile();
        log.info(patientResDto.getFirstName());
        return ResponseEntity.status(HttpStatus.OK).body(patientResDto);
    }

    @GetMapping("/search/{firstName}/{specialization}/{city}")
    public ResponseEntity<List<DoctorResDto>> getdoctor(@PathVariable String firstName, @PathVariable String specialization , @PathVariable String city){
         List<DoctorResDto> doctorResDto=patientSerivce.searchDoctor(firstName,specialization,city);
         return ResponseEntity.status(HttpStatus.OK).body(doctorResDto);

    }

    @GetMapping("/search/by/{id}")
    public ResponseEntity<DoctorResDto> finddoctor(@PathVariable Long id){
        DoctorResDto doctorResDto=patientSerivce.getDoctorById(id);
        return ResponseEntity.status(HttpStatus.OK).body(doctorResDto);
    }

   @GetMapping("all/doctors")
    public ResponseEntity<List<DoctorResDto>> alldoctors(){
        List<DoctorResDto> doctorResDto= patientSerivce.getAllDoctors();
        return ResponseEntity.status(HttpStatus.OK).body(doctorResDto);

   }
}
