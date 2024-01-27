package com.rpp95.bookstorerest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rpp95.bookstorerest.entities.Address;

public interface AddressRepository extends JpaRepository<Address, Integer>{
    
}
