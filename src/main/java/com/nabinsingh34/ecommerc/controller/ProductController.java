package com.nabinsingh34.ecommerc.controller;

import com.nabinsingh34.ecommerc.dto.ProductRequest;
import com.nabinsingh34.ecommerc.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping(value = "/user", produces = "application/json")
    public ResponseEntity<?> create(@Valid @ModelAttribute ProductRequest productRequest) throws IOException {
        productService.createProduct(productRequest);
        return ResponseEntity.status(HttpStatus.OK).body("Image saved");

    }

    @GetMapping("/hello")
    public String admin(){
        return "i am admin";
    }
}