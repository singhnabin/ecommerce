package com.nabinsingh34.ecommerc.service;

import com.nabinsingh34.ecommerc.exception.SpringEcommerceException;
import com.nabinsingh34.ecommerc.model.User;
import com.nabinsingh34.ecommerc.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       Optional<User> userOptional= userRepository.findByEmail(username);
       User user=userOptional.orElseThrow(()->new UsernameNotFoundException("User not found in db"));

        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),user.isEnabled(),true,true,true,getAuthorities(user.getRole()));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }
}
