package com.nabinsingh34.ecommerc.service;

import com.nabinsingh34.ecommerc.dto.EditUser;
import com.nabinsingh34.ecommerc.dto.LoginRequest;
import com.nabinsingh34.ecommerc.dto.UserRequest;
import com.nabinsingh34.ecommerc.exception.SpringEcommerceException;
import com.nabinsingh34.ecommerc.model.NotificationEmail;
import com.nabinsingh34.ecommerc.model.User;
import com.nabinsingh34.ecommerc.model.VerificationToken;
import com.nabinsingh34.ecommerc.repo.UserRepository;
import com.nabinsingh34.ecommerc.repo.VerificationRepository;
import com.nabinsingh34.ecommerc.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationRepository verificationRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    JwtUtils jwtUtils;


    public void createUser(UserRequest userRequest) {
        User user= new User();
        user.setEmail(userRequest.getEmail());
        user.setFirst_name(userRequest.getFirst_name());
        user.setLast_name(userRequest.getLast_name());
        user.setPassword(encodePassword(userRequest.getPassword()));
        user.setEnabled(false);
        user.setRole("USER");
        userRepository.save(user);
        String activateLink="Thank you for singing up. Please click the link below ur to activate your account:  http://localhost:8080/api/user/accountVerification/";
        String token = generateVerificationToken(user);
        mailService.sendMail(new NotificationEmail("Please Activate your account",user.getEmail(),activateLink+token));
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    private String generateVerificationToken(User user) {
        String token= UUID.randomUUID().toString();
        VerificationToken verificationToken= new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationRepository.save(verificationToken);
        return token;

    }

    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationTokenOptional=verificationRepository.findByToken(token);
        System.out.println(verificationTokenOptional);
        verificationTokenOptional.orElseThrow(()->new SpringEcommerceException("Invalid token"));
        verificationTokenOptional.ifPresent(verificationToken -> fetchUserAndEnableUser(verificationTokenOptional.get().getUser()));
    }

    private void fetchUserAndEnableUser(User user) {
//        userRepository.findOne(user.getEmail());
        Optional<User> userExist= Optional.ofNullable(userRepository.findByEmail(user.getEmail()).orElseThrow(() -> new SpringEcommerceException("Email not found in the database")));
        if(userExist.isPresent()) {
            userExist.get().setEnabled(true);
            userRepository.save(userExist.get());
        }

    }

    public String login(LoginRequest loginRequest) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        } catch (BadCredentialsException e){
            throw new Exception("Incorrect user name or password");
        }
        final UserDetails userDetails= userDetailsService.loadUserByUsername(loginRequest.getEmail());
        String jwt= jwtUtils.generateToken(userDetails);
        return jwt;
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> getUserById(int user_id) {
        return userRepository.findById(user_id);
    }




    public User mapOptionUserToUser(User userExists) {
            User user= new User();
            user.setRole(userExists.getRole());
            user.setFirst_name(userExists.getFirst_name());
            user.setLast_name(userExists.getLast_name());
            user.setEmail(userExists.getEmail());

            return user;


    }


    public void editUserDetails(EditUser editUser,User user) {
        user.setLast_name(editUser.getLast_name());
        user.setFirst_name(editUser.getFirst_name());
        user.setEmail(editUser.getEmail());
        userRepository.save(user);
    }

    public void deleteById(int user_id) {
        userRepository.deleteById(user_id);
    }

    public List<User> getAllUserById() {
        return userRepository.findAll();
    }
}
