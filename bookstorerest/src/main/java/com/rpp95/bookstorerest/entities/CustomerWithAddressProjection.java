package com.rpp95.bookstorerest.entities;

import org.springframework.data.rest.core.config.Projection;

@Projection(name="withAddress", types={Customer.class})
public interface CustomerWithAddressProjection {
    Integer getId();
    String getName();
    String getEmail();
    String getCustAddress();

}
