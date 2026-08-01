package com.project.project.Serivce;

import com.project.project.Dto.User.LoginReqDto;
import com.project.project.Dto.User.UserMapping;
import com.project.project.Dto.User.UserReqDto;
import com.project.project.Dto.User.UserResDto;
import com.project.project.Email.EmailService;
import com.project.project.Entity.Role;
import com.project.project.Entity.User;
import com.project.project.Repo.UserRepo;
import com.project.project.Security.JwtUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepo userRepo;
    private final UserMapping userMapping;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final JwtUtils jwtUtils;

    // ================= REGISTER =================
    @Transactional
    public UserResDto register(UserReqDto userReqDto) {

        log.info("REGISTER REQUEST | email={} role={}",
                userReqDto.getEmail(),
                userReqDto.getRole());

//        if (userRepo.findByEmail(userReqDto.getEmail()).isPresent()) {
//
//            log.warn("REGISTER FAILED | Email already exists | email={}",
//                    userReqDto.getEmail());
//
//            throw new RuntimeException("Email already exists");
//        }

        if (userReqDto.getRole() != Role.DOCTOR &&
                userReqDto.getRole() != Role.PATIENT) {

            log.warn("REGISTER FAILED | Invalid role={} email={}",
                    userReqDto.getRole(),
                    userReqDto.getEmail());

            throw new RuntimeException("Invalid Role");
        }

        log.info("Mapping DTO to Entity");

        User user = userMapping.toEntity(userReqDto);

        log.info("Encoding password");

        user.setPassword(passwordEncoder.encode(userReqDto.getPassword()));
        user.setVerified(false);
        user.setProfileCompleted(false);

        log.info("Generating verification token");

        String token = UUID.randomUUID().toString();

        user.setVerificationToken(token);
        user.setVerificationTokenExpiry(
                LocalDateTime.now().plusMinutes(15)
        );

        log.info("Saving user into database");

        User saved = userRepo.save(user);

        log.info("User saved successfully | id={} email={}",
                saved.getId(),
                saved.getEmail());

        String link = "http://localhost:5173/verify-email?token=" + token;

        log.info("Sending verification email | email={}",
                saved.getEmail());

        emailService.sendVerificationEmail(
                saved.getEmail(),
                saved.getName(),
                link
        );

        log.info("Verification email sent successfully | email={}",
                saved.getEmail());

        log.info("REGISTER SUCCESS | id={} role={}",
                saved.getId(),
                saved.getRole());

        return userMapping.toDto(saved);
    }

    // ================= VERIFY EMAIL =================

    public String verifyEmail(String token) {

        log.info("VERIFY EMAIL REQUEST");

        User user = userRepo.findByVerificationToken(token)
                .orElseThrow(() -> {

                    log.error("VERIFY FAILED | Invalid token");

                    return new RuntimeException("Invalid verification link");
                });

        log.info("User found | email={}", user.getEmail());

        if (Boolean.TRUE.equals(user.getVerified())) {

            log.warn("VERIFY FAILED | Already verified | email={}",
                    user.getEmail());

            throw new RuntimeException("Email already verified");
        }

        if (user.getVerificationTokenExpiry() == null ||
                user.getVerificationTokenExpiry().isBefore(LocalDateTime.now())) {

            log.warn("VERIFY FAILED | Token expired | email={}",
                    user.getEmail());

            throw new RuntimeException("Verification link expired");
        }

        log.info("Updating verification status");

        user.setVerified(true);
        user.setVerificationToken(null);
        user.setVerificationTokenExpiry(null);

        userRepo.save(user);

        log.info("VERIFY SUCCESS | email={}", user.getEmail());

        return "Email verified successfully";
    }

    // ================= LOGIN =================

    public User login(LoginReqDto loginReqDto) {

        log.info("LOGIN REQUEST | email={}",
                loginReqDto.getEmail());

        User user = userRepo.findByEmail(loginReqDto.getEmail())
                .orElseThrow(() -> {

                    log.warn("LOGIN FAILED | User not found | email={}",
                            loginReqDto.getEmail());

                    return new RuntimeException("User not found");
                });

        log.info("User found | id={} role={}",
                user.getId(),
                user.getRole());

        if (!Boolean.TRUE.equals(user.getVerified())) {

            log.warn("LOGIN FAILED | Email not verified | email={}",
                    user.getEmail());

            throw new RuntimeException("Email not verified");
        }

        log.info("Checking password");

        if (!passwordEncoder.matches(
                loginReqDto.getPassword(),
                user.getPassword())) {

            log.warn("LOGIN FAILED | Invalid password | email={}",
                    user.getEmail());

            throw new RuntimeException("Invalid credentials");
        }

        log.info("LOGIN SUCCESS | id={} email={} role={}",
                user.getId(),
                user.getEmail(),
                user.getRole());

        return user;
    }

    // ================= REFRESH =================

    public String refresh(String refreshToken) {

        log.info("REFRESH TOKEN REQUEST");

        if (refreshToken == null) {

            log.warn("REFRESH FAILED | Refresh token missing");

            throw new RuntimeException("Invalid refresh token");
        }

        if (!jwtUtils.validate(refreshToken)) {

            log.warn("REFRESH FAILED | Invalid refresh token");

            throw new RuntimeException("Invalid refresh token");
        }

        log.info("Refresh token validated");

        String email = jwtUtils.getEmail(refreshToken);
        String role = jwtUtils.getRole(refreshToken);

        log.info("Generating new access token | email={} role={}",
                email,
                role);

        String accessToken = jwtUtils.accessToken(role, email);

        log.info("REFRESH SUCCESS | email={}", email);

        return accessToken;
    }

    // ================= ME =================

    public UserResDto me(String email) {

        log.info("ME REQUEST | email={}", email);

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> {

                    log.warn("ME FAILED | User not found | email={}",
                            email);

                    return new RuntimeException("User not found");
                });

        log.info("ME SUCCESS | id={} role={}",
                user.getId(),
                user.getRole());

        return userMapping.toDto(user);
    }
}