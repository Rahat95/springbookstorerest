package com.rpp95.bookstorerest.services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rpp95.bookstorerest.entities.User;
import com.rpp95.bookstorerest.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User findUser = this.userRepository
    .findByEmail(username)
    .orElseThrow(() ->{
         throw new UsernameNotFoundException("User with this email does not exist");
    });

    Collection<GrantedAuthority> authorities = new ArrayList<>();

    for(String role : findUser.getRoles()){
        authorities.add(new SimpleGrantedAuthority(role));
    }

    User user = new User(findUser.getEmail(),
                            findUser.getPassword(),
                            authorities);

    // User user = new User(findUser.getEmail(),
    //                     findUser.getPassword(),
    //                     authorities);

    return user;
    
    }
    
}
