package com.project.project.Dto.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResDto {

    private String message;

    private String accessToken;

    private String refreshToken;

    private String role;
}