package com.ecommerce.authentication.service;

import com.ecommerce.authentication.dao.UserDAO;
import com.ecommerce.authentication.exception.AuthenticationException;
import com.ecommerce.authentication.model.User;
import com.ecommerce.authentication.model.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> userOptional = userDAO.findByUsername(username);

        userOptional.orElseThrow(() -> new AuthenticationException("No user found with username: " + username));

        return userOptional.map(UserDetailsImpl::new).get();
    }
}
