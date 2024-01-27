package com.rpp95.bookstorerest.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;

import com.rpp95.bookstorerest.entities.Book;
import com.rpp95.bookstorerest.entities.BookPartialProjection;

import jakarta.annotation.security.RolesAllowed;

@RepositoryRestResource(excerptProjection = BookPartialProjection.class)
public interface BookRepository extends JpaRepository<Book,Integer>{
    //jpa is comibination of crud and paging and sorting repos;

    List<Book> findByTitle(String title);

    @PreAuthorize("hasRole('HR')")
    @RestResource(path = "byname")
    List<Book> findByAuthorName(String name);

    @RolesAllowed("IT")
    @RestResource(path = "pricegreat")
    List<Book> findByPriceGreaterThan(Float value);
    
    @Secured("ROLE_ADMIN")
    @RestResource(path = "priceless")
    List<Book> findByPriceLessThan(Float value);
    
    //postman queries for this are in notepad restapi
    @RestResource(path = "between")
    List<Book> findByPriceBetween(Float low, Float high);
    
    @RestResource(path = "titlestart")
    List<Book> findByTitleStartsWithOrTitleStartsWith(String letter1,String letter2);    

    @RestResource(path = "titleprice")
    List<Book> findByTitleStartsWithAndPriceGreaterThan(String letter, Float value);
}
