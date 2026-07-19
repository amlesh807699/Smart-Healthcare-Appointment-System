package com.project.project.Serivce;

import com.project.project.Dto.User.UserMapping;
import com.project.project.Dto.User.UserReqDto;
import com.project.project.Dto.User.UserResDto;
import com.project.project.Entity.Role;
import com.project.project.Entity.User;
import com.project.project.Repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepo userRepo;

    private final PasswordEncoder passwordEncoder;

    private final UserMapping userMapping;

    public UserResDto register(UserReqDto userReqDto){

        if(userRepo.findByEmail(userReqDto.getEmail()).isPresent()){

            throw new RuntimeException(
                    "Email already exists"
            );
        }

        if(userReqDto.getRole()!= Role.DOCTOR &&
                userReqDto.getRole()!=Role.PATIENT){

            throw new RuntimeException(
                    "Invalid role"
            );
        }

        User user=new User();

        user.setName(userReqDto.getName());

        user.setSurname(userReqDto.getSurname());

        user.setEmail(userReqDto.getEmail());

        user.setPassword(passwordEncoder.encode(userReqDto.getPassword()));

        user.setRole(
                userReqDto.getRole()
        );

        user.setVerified(false);

        user.setProfileCompleted(false);

        String token= UUID.randomUUID().toString();
        user.setVerificationToken(token);
        user.setVerificationTokenExpiry(LocalDateTime.now().plusMinutes(15));
        User savedUser =
                userRepo.save(user);



        return userMapping.toDto(savedUser);

    }

    public UserResDto verify(String token){
        User user
    }


}
