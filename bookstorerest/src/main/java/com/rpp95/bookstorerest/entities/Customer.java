package com.rpp95.bookstorerest.entities;

import java.time.Instant;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;

import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;


@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String email;

    //owner entity
    @OneToOne
    @JoinColumn(name = "address_id")
    private Address custAddress;

    @CreatedDate
    private Instant createdAt;
    @CreatedDate
    @Column(nullable = false, updatable = false)
    @LastModifiedDate
    private Instant updatedAt;   
    
    //owner entity
    @ManyToMany
    @JoinTable(
        name = "customer_book",
        joinColumns = @JoinColumn(name = "customer_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id")
    )
    private List<Book> purchasedBooks;
}

