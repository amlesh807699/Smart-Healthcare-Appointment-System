package com.project.project.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {


    private final JwtUtils jwtUtils;


    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {


        log.info("JWT FILTER START | URI={}", request.getRequestURI());


        String token = null;


        // 1. Cookie se token lo
        if (request.getCookies() != null) {


            log.info("Cookies found | count={}", request.getCookies().length);


            for (Cookie cookie : request.getCookies()) {


                log.info("Cookie name={}", cookie.getName());


                if ("token".equals(cookie.getName())) {

                    token = cookie.getValue();

                    log.info("JWT TOKEN FOUND IN COOKIE");

                    break;
                }
            }

        }
        else {

            log.info("No cookies found");

        }








        if (token != null) {


            log.info("JWT TOKEN EXISTS");


            boolean valid = jwtUtils.validate(token);


            log.info("JWT VALIDATION RESULT={}", valid);



            if (valid) {



                String type = jwtUtils.extractType(token);


                log.info("JWT TYPE={}", type);



                // Access Token hi allow karo
                if (!"access".equals(type)) {


                    log.warn("INVALID TOKEN TYPE | type={}", type);


                    filterChain.doFilter(request, response);

                    return;

                }



                String email = jwtUtils.getEmail(token);

                String role = jwtUtils.getRole(token);



                log.info(
                        "JWT USER DETAILS | email={} | role={}",
                        email,
                        role
                );



                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                email,
                                null,
                                List.of(
                                        new SimpleGrantedAuthority(
                                                "ROLE_" + role
                                        )
                                )
                        );



                authentication.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );



                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authentication);



                log.info(
                        "SECURITY CONTEXT SET | authority=ROLE_{}",
                        role
                );


            }


        }
        else {


            log.warn("JWT TOKEN NOT FOUND");


        }



        filterChain.doFilter(request, response);


        log.info("JWT FILTER END | URI={}", request.getRequestURI());

    }
}