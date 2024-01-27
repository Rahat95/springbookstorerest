package com.rpp95.bookstorerest.entities;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class Book {

@Id
@GeneratedValue(strategy = GenerationType.AUTO) //genertedvalue jakarta.persistance
private Integer id;

@NotNull(message="title must not be null")
@NotEmpty(message="title must not be empty")
@Size(min=3, max=15, message="title must have 3 to 15 characters")
private String title;

@NotNull(message="author must not be null")
@NotEmpty(message="author must not be empty")
private String authorName;

@NotNull(message="price must not be null")
@Min(value=0)
@Max(value=2000)
private Float price; 

@Size(min=10, message="title must have 10 characters")
private String description;

//@Past
//@PastOrPresent
//@Future
@Past
private LocalDate publishedDate;  

@CreatedDate
private Instant createdAt;

@LastModifiedDate
private Instant updateAt;

//owner entity
@ManyToOne
@JoinColumn(name = "publisher_id")
private Publisher publisher;

//inverse entity
@ManyToMany(mappedBy = "purchasedBooks")
private List<Customer> customers;
}
