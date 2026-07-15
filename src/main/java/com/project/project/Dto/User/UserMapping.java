package com.project.project.Dto.User;

import com.project.project.Entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapping {

    User toEntity(UserReqDto userReqDto);
    UserResDto toDto(User user);

    List<UserResDto> toResponseDto(List<User> users);

    void updateUserFromDto(UserReqDto reqDto,
                           @MappingTarget User user);

}
