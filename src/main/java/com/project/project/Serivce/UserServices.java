package com.project.project.Serivce;

import com.project.project.Dto.User.UserMapping;
import com.project.project.Dto.User.UserReqDto;
import com.project.project.Dto.User.UserResDto;
import com.project.project.Entity.User;
import com.project.project.Repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public abstract class UserServices{
    private final UserRepo userRepo;
    private UserMapping userMapping;

    public UserResDto save(UserReqDto userReqDto){
        User user=userMapping.toEntity(userReqDto);
        User saveduser=userRepo.save(user);
        return userMapping.toDto(saveduser);
    }

    public UserResDto byid(Long id){
        User user=userRepo.findById(id).orElse(null);
        return userMapping.toDto(user);
    }


    public List<UserResDto> all() {
        List<User> users=userRepo.findAll();
        return userMapping.toResponseDto(users);
    }



    public void delete(Long id) {

        User user = userRepo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        userRepo.delete(user);
    }
}
