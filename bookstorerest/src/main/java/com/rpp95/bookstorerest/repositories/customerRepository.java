package com.rpp95.bookstorerest.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import com.rpp95.bookstorerest.entities.Customer;

public interface customerRepository  extends JpaRepository<Customer, Integer>{
    
}
