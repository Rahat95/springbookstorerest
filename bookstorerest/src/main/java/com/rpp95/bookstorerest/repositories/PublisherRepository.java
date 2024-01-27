package com.rpp95.bookstorerest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rpp95.bookstorerest.entities.Publisher;

public interface PublisherRepository extends JpaRepository<Publisher, Integer>{
    
}
