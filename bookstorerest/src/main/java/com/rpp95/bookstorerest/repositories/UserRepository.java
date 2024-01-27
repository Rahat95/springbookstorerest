package com.rpp95.bookstorerest.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rpp95.bookstorerest.entities.User;

@Repository
public interface UserRepository 
extends JpaRepository<User, Integer>{
    
    Optional<User> findByEmail(String email);

    
    
}
