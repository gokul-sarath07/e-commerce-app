package com.ecommerce.authentication.service;

import com.ecommerce.authentication.dao.UserDAO;
import com.ecommerce.authentication.model.User;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class UserService {

    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDAO.saveAndFlush(user);
    }

    public boolean checkIfUsernameExist(String username) {
        Optional<User> userOptional = userDAO.findByUsername(username);
        if(userOptional.isEmpty()) { return false; }
        return true;
    }

    public void updateUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDAO.save(user);
    }

    public void deleteUser(User user) {
        userDAO.delete(user);
    }
}
