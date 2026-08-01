package com.project.project.Serivce;


import com.project.project.CloudNairy.CloudinaryService;
import com.project.project.Dto.Appointment.*;
import com.project.project.Dto.Doctor.*;
import com.project.project.Dto.Patient.*;
import com.project.project.Dto.Report.*;
import com.project.project.Entity.*;

import com.project.project.Repo.*;
import com.project.project.UserSerivce.UserSerivce;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PatientSerivce{


    private final PatientRepo patientRepo;
    private final Doctorrepo doctorRepo;
    private final AppointmentRepo appointmentRepo;
    private final ReportRepo reportRepo;

    private final DoctorMapping doctorMapping;
    private final PatientMapping patientMapping;
    private final AppointmentMapper appointmentMapper;
    private final ReportMapper reportMapper;

    private final UserSerivce userService;
    private final CloudinaryService cloudinaryService;



    // CREATE PROFILE

    public PatientResDto addProfile(
            PatientReqDto dto,
            MultipartFile profilePic
    ){


        User user=getCurrentUser();


        if(patientRepo.findByUser(user).isPresent()){
            throw new RuntimeException(
                    "Patient profile already exists"
            );
        }


        Patient patient=patientMapping.toEntity(dto);



        if(profilePic!=null && !profilePic.isEmpty()){

            String imageUrl =
                    cloudinaryService.uploadImage(profilePic);

            patient.setProfilepic(imageUrl);
        }



        patient.setUser(user);



        Patient saved =
                patientRepo.save(patient);



        return patientMapping.toDto(saved);

    }






    // GET OWN PROFILE


    @Transactional(readOnly = true)
    public PatientResDto getMyProfile(){

        log.info("GET PROFILE REQUEST START");

        User user = userService.getCurrentUser();

        if(user == null){
            log.error("CURRENT USER NOT FOUND");
            throw new RuntimeException("User not authenticated");
        }

        log.info("CURRENT USER FOUND | id={} | email={} | role={}",
                user.getId(),
                user.getEmail(),
                user.getRole()
        );


        Patient patient = patientRepo.findByUser(user).orElse(null);


        if(patient == null){

            log.error("PATIENT PROFILE NOT FOUND | userId={}",
                    user.getId()
            );

            throw new RuntimeException("Patient profile not found");
        }


        log.info("PATIENT FOUND | patientId={} | firstName={} | lastName={}",
                patient.getId(),
                patient.getFirstName(),
                patient.getLastName()
        );


        PatientResDto dto = patientMapping.toDto(patient);


        log.info("PATIENT DTO CREATED SUCCESSFULLY");


        log.info("GET PROFILE REQUEST END");


        return dto;
    }






    // SEARCH DOCTOR


    @Transactional(readOnly = true)
    public List<DoctorResDto> searchDoctor(
            String firstName,
            String specialization,
            String city
    ){


        List<Doctor> doctors =
                doctorRepo
                        .findByfirstNameAndSpecializationAndCity(
                                firstName,
                                specialization,
                                city
                        );


        return doctorMapping.toDtoList(doctors);

    }






    // GET DOCTOR BY ID


    @Transactional(readOnly = true)
    public DoctorResDto getDoctorById(Long id){


        Doctor doctor =
                doctorRepo.findById(id)
                        .orElseThrow(
                                ()->new RuntimeException(
                                        "Doctor not found"
                                ));



        return doctorMapping.toDto(doctor);

    }





    // ALL DOCTORS


    @Transactional(readOnly = true)
    public List<DoctorResDto> getAllDoctors(){


        return doctorMapping.toDtoList(
                doctorRepo.findAll()
        );

    }






    // BOOK APPOINTMENT


    public AppointmentResDto bookAppointment(
            AppointmentReqDto dto
    ){



        Patient patient=getCurrentPatient();



        Doctor doctor =
                doctorRepo.findById(dto.getDoctorId())
                        .orElseThrow(
                                ()->new RuntimeException(
                                        "Doctor not found"
                                ));



        Appointment appointment =
                appointmentMapper.toEntity(dto);



        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setStatus(
                AppointmentStatus.BOOKED
        );



        Appointment saved =
                appointmentRepo.save(appointment);



        return appointmentMapper.toDto(saved);

    }







    // ALL APPOINTMENTS


    @Transactional(readOnly = true)
    public List<AppointmentResDto> getMyAppointments(){


        Patient patient=getCurrentPatient();



        return appointmentMapper.toDto(
                appointmentRepo.findByPatient(patient)
        );

    }






    // APPOINTMENT BY ID


    @Transactional(readOnly = true)
    public AppointmentResDto getMyAppointmentById(
            Long id
    ){


        Patient patient=getCurrentPatient();



        Appointment appointment =
                appointmentRepo
                        .findByIdAndPatient(id,patient)
                        .orElseThrow(
                                ()->new RuntimeException(
                                        "Appointment not found"
                                ));



        return appointmentMapper.toDto(
                appointment
        );

    }







    // DELETE APPOINTMENT


    public String deleteAppointment(
            Long id
    ){


        Patient patient=getCurrentPatient();



        Appointment appointment =
                appointmentRepo
                        .findByIdAndPatient(id,patient)
                        .orElseThrow(
                                ()->new RuntimeException(
                                        "Appointment not found"
                                ));



        appointmentRepo.delete(appointment);



        return "Appointment deleted successfully";

    }








    // ALL REPORTS


    @Transactional(readOnly = true)
    public List<ReportResDto> getMyReports(){


        Patient patient=getCurrentPatient();



        return reportMapper.toDto(
                reportRepo.findByPatient(patient)
        );


    }






    // REPORT BY ID


    @Transactional(readOnly = true)
    public ReportResDto getMyReportById(
            Long id
    ){



        Report report =
                reportRepo.findById(id)
                        .orElseThrow(
                                ()->new RuntimeException(
                                        "Report not found"
                                ));



        return reportMapper.ToDto(report);

    }








    // HELPER METHODS


    private User getCurrentUser(){

        return userService.getCurrentUser();

    }




    private Patient getCurrentPatient(){


        User user=getCurrentUser();


        return patientRepo.findByUser(user)
                .orElseThrow(
                        ()->new RuntimeException(
                                "Patient profile not found"
                        ));


    }



}