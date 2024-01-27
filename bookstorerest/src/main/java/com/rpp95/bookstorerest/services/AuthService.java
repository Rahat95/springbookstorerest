package com.rpp95.bookstorerest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.rpp95.bookstorerest.entities.User;
import com.rpp95.bookstorerest.repositories.UserRepository;

@Service
public class AuthService {
 
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public String registerUser(User user){
        if(this.userRepository.findByEmail(user.getEmail()).isPresent()){ 
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with this email already exists");
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        this.userRepository.save(user);
        return "User registered Successfully";
    }
}
