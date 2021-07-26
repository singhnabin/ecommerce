package com.nabinsingh34.ecommerc.controller;

import com.nabinsingh34.ecommerc.dto.UserRequest;
import com.nabinsingh34.ecommerc.error.ErrorApiResponse;
import com.nabinsingh34.ecommerc.model.User;
import com.nabinsingh34.ecommerc.service.UserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/users", produces = "application/json")
    public ResponseEntity<?> getAllUsers(){
        JSONObject jsonObject= new JSONObject();
        jsonObject.put("statuscode",200);
        jsonObject.put("message","success");

       List<User> users=userService.getAllUserById();
        jsonObject.put("users",users);
        return ResponseEntity.status(HttpStatus.OK).body(jsonObject.toString());
    }

    @GetMapping(value = "/accountVerification/{token}",produces = "application/json")
    public ResponseEntity<?> verifyToken(@PathVariable String token){
        userService.verifyAccount(token);
        return ResponseEntity.status(HttpStatus.OK).body("Account activated successfully");
    }


}
