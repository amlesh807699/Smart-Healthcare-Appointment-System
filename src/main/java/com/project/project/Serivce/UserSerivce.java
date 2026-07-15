package com.project.project.Serivce;

import com.project.project.Dto.UserReqDto;
import com.project.project.Dto.UserResDto;
import com.project.project.Repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserSerivce {

    UserResDto save(UserReqDto userReqDto);
    UserResDto byid(Long id);
    List<UserResDto> all();
    UserResDto update(Long id,UserReqDto userReqDto);
    void delete(Long id);

}
