package com.nabinsingh34.ecommerc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

   @GetMapping("/hello")
    public String admin(){
       return "i am admin";
   }
}
