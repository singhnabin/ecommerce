package com.nabinsingh34.ecommerc.controller;

import com.nabinsingh34.ecommerc.dto.LoginRequest;
import com.nabinsingh34.ecommerc.dto.LoginResponse;
import com.nabinsingh34.ecommerc.dto.UserRequest;
import com.nabinsingh34.ecommerc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/create", produces = "application/json")
    public ResponseEntity<?> createUser( @RequestBody @Valid UserRequest userRequest, Errors errors){
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


//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public Map<String, String> handleValidationExceptions(
//            MethodArgumentNotValidException ex) {
//        Map<String, String> errors = new HashMap<>();
//        ex.getBindingResult().getAllErrors().forEach((error) -> {
//            String fieldName = ((FieldError) error).getField();
//            String errorMessage = error.getDefaultMessage();
//            errors.put(fieldName, errorMessage);
//        });
//        return errors;
//    }
}
