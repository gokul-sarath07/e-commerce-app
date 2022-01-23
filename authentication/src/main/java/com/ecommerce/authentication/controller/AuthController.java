package com.ecommerce.authentication.controller;

import com.ecommerce.authentication.model.User;
import com.ecommerce.authentication.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import com.ecommerce.authentication.model.Error;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth/")
public class AuthController {

    private final UserService userService;
    private static String username = "Please login and try again.";


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid User user, Errors errors) {
        if(errors.hasErrors()) {
            List<Error> errorList = new Error().getErrorDetails(errors.getAllErrors());
            return new ResponseEntity<List<Error>>(errorList, HttpStatus.BAD_REQUEST);
        }

        if(userService.checkIfUsernameExist(user.getUsername())) {
            return new ResponseEntity<>("Username already exists.", HttpStatus.BAD_REQUEST);
        }

        userService.registerUser(user);
        return new ResponseEntity<>("User Registration Successful.", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser() {
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            username = ((UserDetails) principal).getUsername();

            return new ResponseEntity<>(username + " signed-in successfully.", HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/current-user")
    public ResponseEntity<String> getUsername() {
        return new ResponseEntity<>(username, HttpStatus.OK);
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout() {
        username = "Please login and try again.";
        return new ResponseEntity<>("You have been logged out.", HttpStatus.OK);
    }

}