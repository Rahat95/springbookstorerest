package com.rpp95.bookstorerest.entities;

import org.springframework.data.rest.core.config.Projection;

@Projection(name="bookpartial", types = {Book.class})
public interface BookPartialProjection {

String getTitle();
String getAuthorName();
Float getPrice();
Publisher getPublisher();

}
