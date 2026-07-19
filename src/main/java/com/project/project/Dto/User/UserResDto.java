package com.project.project.Dto.User;


import com.project.project.Entity.Role;
import lombok.Data;

@Data
public class UserResDto {

    private Long id;

    private String name;

    private String surname;

    private String email;

    private Role role;

    private Boolean verified;
}