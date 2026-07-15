package com.project.project.Serivce;

import com.project.project.Dto.User.UserReqDto;
import com.project.project.Dto.User.UserResDto;

import java.util.List;

public interface UserSerivce {

    UserResDto save(UserReqDto userReqDto);
    UserResDto byid(Long id);
    List<UserResDto> all();
    UserResDto update(Long id,UserReqDto userReqDto);
    void delete(Long id);

}
