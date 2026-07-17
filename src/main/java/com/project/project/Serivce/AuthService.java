package com.project.project.Serivce;

import com.project.project.Dto.User.UserMapping;
import com.project.project.Dto.User.UserReqDto;
import com.project.project.Dto.User.UserResDto;
import com.project.project.Entity.User;
import com.project.project.Repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepo userRepo;
    private final UserMapping userMapping;

    public UserResDto register(UserReqDto userReqDto){
        User user=userRepo.findByEmail(userReqDto.getEmail()).orElse(null
        );
        User user1=u
    }



}
