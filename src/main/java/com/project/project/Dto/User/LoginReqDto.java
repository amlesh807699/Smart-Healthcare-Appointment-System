package com.project.project.Dto.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginReqDto {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

}