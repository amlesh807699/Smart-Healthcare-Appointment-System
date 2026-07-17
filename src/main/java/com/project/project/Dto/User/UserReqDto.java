package com.project.project.Dto.User;


import com.project.project.Entity.Role;
import lombok.Data;

@Data
public class UserReqDto {
    private String name;
    private String surname;
    private String email;
    private String password;
    private Role role;
}
