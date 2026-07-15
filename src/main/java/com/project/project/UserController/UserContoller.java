package com.project.project.UserController;


import com.project.project.Dto.User.UserReqDto;
import com.project.project.Dto.User.UserResDto;
import com.project.project.Serivce.UserServices;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserContoller {

    private UserServices userServices;


    @PostMapping("/add")
    private ResponseEntity<UserResDto> add(@RequestBody  UserReqDto userReqDto){

        UserResDto userResDto=userServices.save(userReqDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userResDto);

    }

    private ResponseEntity<UserResDto> one( @Valid  @PathVariable Long id){
        UserResDto resDto=userServices.byid(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(resDto);
    }
}
