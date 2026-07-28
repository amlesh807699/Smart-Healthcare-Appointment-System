package com.project.project.Dto.User;

import com.project.project.Entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapping {


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "name")
    @Mapping(target = "surname", source = "surname")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "verified", ignore = true)
    @Mapping(target = "verificationToken", ignore = true)
    @Mapping(target = "verificationTokenExpiry", ignore = true)
    @Mapping(target = "profileCompleted", ignore = true)
    User toEntity(UserReqDto userReqDto);


    UserResDto toDto(User user);


    List<UserResDto> toResponseDto(List<User> users);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "verified", ignore = true)
    @Mapping(target = "verificationToken", ignore = true)
    @Mapping(target = "verificationTokenExpiry", ignore = true)
    @Mapping(target = "profileCompleted", ignore = true)
    void updateUserFromDto(
            UserReqDto reqDto,
            @MappingTarget User user
    );

}