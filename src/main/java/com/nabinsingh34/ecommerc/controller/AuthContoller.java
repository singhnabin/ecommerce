package com.nabinsingh34.ecommerc.controller;

import com.nabinsingh34.ecommerc.dto.LoginRequest;
import com.nabinsingh34.ecommerc.dto.LoginResponse;
import com.nabinsingh34.ecommerc.dto.UserRequest;
import com.nabinsingh34.ecommerc.error.ErrorApiResponse;
import com.nabinsingh34.ecommerc.model.User;
import com.nabinsingh34.ecommerc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@Validated
public class AuthContoller {
    @Autowired
    private UserService userService;

    @PostMapping(value = "/user", produces = "application/json")
    public ResponseEntity<?> createUser(@RequestBody @Valid UserRequest userRequest, BindingResult bindingResult){
        Optional<User> user= userService.findUserByEmail(userRequest.getEmail());
        if(user.isPresent()){
            return ResponseEntity.ok(ErrorApiResponse.builder().message(userRequest.getEmail()+" already exist.").statusCode(409).build());
        }
        userService.createUser(userRequest);
        return ResponseEntity.status(HttpStatus.OK).body("User created successfully");
    }

    @GetMapping(value = "/accountVerification/{token}",produces = "application/json")
    public ResponseEntity<?> verifyToken(@PathVariable String token){
        userService.verifyAccount(token);
        return ResponseEntity.status(HttpStatus.OK).body("Account activated successfully");
    }


    @PostMapping(value = "/login",produces = "application/json")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) throws Exception {
        String token=userService.login(loginRequest);
        return ResponseEntity.ok(new LoginResponse(200,loginRequest.getEmail(),token)) ;

    }
}
