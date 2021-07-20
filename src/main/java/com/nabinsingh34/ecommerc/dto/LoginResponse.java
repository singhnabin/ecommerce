package com.nabinsingh34.ecommerc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    int status;
    String email;
    String token;

}
