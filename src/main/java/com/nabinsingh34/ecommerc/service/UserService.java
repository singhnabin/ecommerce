package com.nabinsingh34.ecommerc.service;

import com.nabinsingh34.ecommerc.dto.UserRequest;
import com.nabinsingh34.ecommerc.exception.SpringEcommerceException;
import com.nabinsingh34.ecommerc.model.NotificationEmail;
import com.nabinsingh34.ecommerc.model.User;
import com.nabinsingh34.ecommerc.model.VerificationToken;
import com.nabinsingh34.ecommerc.repo.UserRepository;
import com.nabinsingh34.ecommerc.repo.VerificationRepository;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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

    public void createUser(UserRequest userRequest) {
        User user= new User();
        user.setEmail(userRequest.getEmail());
        user.setFirst_name(userRequest.getFirst_name());
        user.setLast_name(userRequest.getLast_name());
        user.setPassword(userRequest.getPassword());
        user.setEnabled(false);
        userRepository.save(user);
        String activateLink="Thank you for singing up. Please click the link below ur to activate your account:  http://localhost:8080/api/user/accountVerification/";
        String token = generateVerificationToken(user);
        mailService.sendMail(new NotificationEmail("Please Activate your account",user.getEmail(),activateLink+token));
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
}