package com.nabinsingh34.ecommerc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditUser {
    @NotBlank(message = "First Name cannot be blank")
    @Size(min = 3,message = "First name should be of at least 3 character")
    private String first_name;

    @NotBlank(message = "Last Name cannot be blank")
    @Size(min = 3,message = "Last name should be of at least 3 character")
    private String last_name;

    @NotBlank(message="email cannont be blank")
    @Email
    private String email;
}
