package com.rpp95.bookstorerest.entities;

import org.springframework.beans.factory.annotation.Value;

public interface BookWithPublisher {
    //open projection
    //target ==> entity class==> here it is Book

    @Value("#{target.title}")
    String getBookTitle();
    String getAuthorName();

    @Value("#{target.publisher.name}")
    String getPublisherName();
}
