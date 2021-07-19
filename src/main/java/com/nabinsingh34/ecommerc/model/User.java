package com.nabinsingh34.ecommerc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@Table(name = "user_db")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    @NotBlank(message="email cannont be blank")
    private String email;

    @Column
    @NotBlank(message = "First Name cannot be blank")
    private String first_name;

    @Column
    @NotBlank(message = "Last Name cannot be blank")
    private String last_name;

    @Column
    @NotBlank(message = "Password cannot be blank")
    private String password;

    @Column
    private boolean isEnabled;



}
