package com.nabinsingh34.ecommerc.controller;


import com.nabinsingh34.ecommerc.customValidation.ValidPassword;
import com.nabinsingh34.ecommerc.dto.EditUser;
import com.nabinsingh34.ecommerc.error.ErrorApiResponse;
import com.nabinsingh34.ecommerc.exception.SpringEcommerceException;
import com.nabinsingh34.ecommerc.model.User;
import com.nabinsingh34.ecommerc.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.sql.Delete;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/user")
@Validated
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{user_id}")
    public ResponseEntity<?> getUserById(@PathVariable int user_id){
        Optional<User> userExists= userService.getUserById(user_id);
        if(userExists.isPresent()){
//            User user= userService.mapOptionUserToUser(userExists.get());
            return ResponseEntity.status(200).body(userExists.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorApiResponse.builder().statusCode(404).message("user with id "+user_id+" no found."));
    }

    @PutMapping(value = "/{user_id}",produces = "application/json")
    public ResponseEntity<?> editUserById(@PathVariable int user_id,@RequestBody @Valid EditUser editUser) throws SpringEcommerceException {
        Optional<User> userExists= userService.getUserById(user_id);

        if(!userExists.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorApiResponse.builder().statusCode(404).message("user with id "+user_id+" no found.").build());
        }
        userService.editUserDetails(editUser,userExists.get());
        return ResponseEntity.status(200).body("User updated successfully");

    }
    @DeleteMapping(value = "/{user_id}",produces = "application/json")
    public ResponseEntity<?> deleteUserById(@PathVariable int user_id){
        Optional<User> userExists= userService.getUserById(user_id);
        if(!userExists.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorApiResponse.builder().statusCode(404).message("user with id "+user_id+" no found.").build());
        }
        userService.deleteById(user_id);
        return ResponseEntity.status(200).body("User with id "+user_id + " deleted successfully");
    }





//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public Map<String, String> handleValidationExceptions(
//            MethodArgumentNotValidException ex) {
//        Map<String, String> errors = new HashMap<>();
//        ex.getBindingResult().getAllErrors().forEach((error) -> {
//            String fieldName = ((FieldError) error).getField();
//            String errorMessage = error.getDefaultMessage();
//            errors.put(fieldName, errorMessage);
//        });
//        return errors;
//    }
}
