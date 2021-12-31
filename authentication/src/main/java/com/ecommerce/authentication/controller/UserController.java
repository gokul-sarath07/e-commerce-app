package com.ecommerce.authentication.controller;

import com.ecommerce.authentication.dto.UserDetailsDTO;
import com.ecommerce.authentication.model.Error;
import com.ecommerce.authentication.model.User;
import com.ecommerce.authentication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody @Valid User user, Errors errors) {
        if(errors.hasErrors()) {
            List<Error> errorList = new Error().getErrorDetails(errors.getAllErrors());
            return new ResponseEntity<List<Error>>(errorList, HttpStatus.BAD_REQUEST);
        }

        if(userService.checkIfUsernameExist(user.getUsername())) {
            return new ResponseEntity<>("Username already exists.", HttpStatus.BAD_REQUEST);
        }

        userService.updateUser(user);
        return new ResponseEntity<>("User has been updated.", HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteUser(@RequestBody User user) {
        userService.deleteUser(user);
        return new ResponseEntity<>("User has been deleted.", HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDetailsDTO> getUserDetailsWithUsername(@RequestBody UserDetailsDTO usernameDTO) {
        UserDetailsDTO userDetailsDTO = userService.getUserDetails(usernameDTO);

        return new ResponseEntity<>(userDetailsDTO, HttpStatus.OK);
    }
}
