package com.nabinsingh34.ecommerc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {


    private String name;


    private String user;

    private MultipartFile file;
}
