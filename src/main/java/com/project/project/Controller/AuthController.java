package com.project.project.Controller;

import com.project.project.Dto.User.LoginReqDto;
import com.project.project.Dto.User.LoginResDto;
import com.project.project.Dto.User.UserReqDto;
import com.project.project.Dto.User.UserResDto;
import com.project.project.Entity.User;
import com.project.project.Security.JwtUtils;
import com.project.project.Serivce.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtUtils jwtUtils;

    // ================= REGISTER =================

    @PostMapping("/register")
    public ResponseEntity<UserResDto> register(
            @Valid @RequestBody UserReqDto userReqDto) {

        UserResDto userResDto = authService.register(userReqDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userResDto);
    }

    // ================= VERIFY EMAIL =================

    @GetMapping("/verify-email")
    public ResponseEntity<String> verifyEmail(
            @RequestParam String token) {

        String message = authService.verifyEmail(token);

        return ResponseEntity.ok(message);
    }

    // ================= LOGIN =================
    @PostMapping("/login")
    public ResponseEntity<String> login(
            @Valid @RequestBody LoginReqDto loginReqDto,
            HttpServletResponse response
    ) {

        log.info("LOGIN REQUEST | email={}", loginReqDto.getEmail());


        User user = authService.login(loginReqDto);


        log.info("USER LOGIN SUCCESS | id={} role={}",
                user.getId(),
                user.getRole()
        );


        String accessToken = jwtUtils.accessToken(
                user.getRole().name(),
                user.getEmail()
        );


        String refreshToken = jwtUtils.refreshToken(
                user.getRole().name(),
                user.getEmail()
        );


        log.info("ACCESS TOKEN GENERATED | length={}",
                accessToken.length()
        );

        log.info("REFRESH TOKEN GENERATED | length={}",
                refreshToken.length()
        );



        Cookie accessCookie = new Cookie("token", accessToken);
        accessCookie.setHttpOnly(true);
        accessCookie.setSecure(false);
        accessCookie.setPath("/");
        accessCookie.setMaxAge(7 * 24 * 60 * 60);



        Cookie refreshCookie = new Cookie("refreshToken", refreshToken);
        refreshCookie.setHttpOnly(true);
        refreshCookie.setSecure(false);
        refreshCookie.setPath("/");
        refreshCookie.setMaxAge(7 * 24 * 60 * 60);



        response.addCookie(accessCookie);
        response.addCookie(refreshCookie);


        log.info("ACCESS COOKIE ADDED | name={} | httpOnly={} | maxAge={}",
                accessCookie.getName(),
                accessCookie.isHttpOnly(),
                accessCookie.getMaxAge()
        );


        log.info("REFRESH COOKIE ADDED | name={} | httpOnly={} | maxAge={}",
                refreshCookie.getName(),
                refreshCookie.isHttpOnly(),
                refreshCookie.getMaxAge()
        );


        return ResponseEntity.ok("Login Successful");
    }
    // ================= REFRESH TOKEN =================

    @PostMapping("/refresh")
    public ResponseEntity<String> refresh(
            HttpServletRequest request,
            HttpServletResponse response) {

        String refreshToken = null;

        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {

                if ("refreshToken".equals(cookie.getName())) {
                    refreshToken = cookie.getValue();
                    break;
                }
            }
        }

        String newAccessToken = authService.refresh(refreshToken);

        Cookie accessCookie = new Cookie("token", newAccessToken);
        accessCookie.setHttpOnly(true);
        accessCookie.setPath("/");
        accessCookie.setMaxAge(15 * 60);

        response.addCookie(accessCookie);

        return ResponseEntity.ok("Token Refreshed");
    }

    // ================= CURRENT USER =================

    @GetMapping("/me")
    public ResponseEntity<UserResDto> me(HttpServletRequest request) {

        String email = (String) request.getAttribute("email");

        if (email == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserResDto user = authService.me(email);

        return ResponseEntity.ok(user);
    }

    // ================= LOGOUT =================

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {

        Cookie accessCookie = new Cookie("token", "");
        accessCookie.setHttpOnly(true);
        accessCookie.setPath("/");
        accessCookie.setMaxAge(0);

        Cookie refreshCookie = new Cookie("refreshToken", "");
        refreshCookie.setHttpOnly(true);
        refreshCookie.setPath("/");
        refreshCookie.setMaxAge(0);

        response.addCookie(accessCookie);
        response.addCookie(refreshCookie);

        return ResponseEntity.ok("Logged Out Successfully");
    }
}