package com.rpp95.bookstorerest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BookstorerestApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookstorerestApplication.class, args);
	}

}
