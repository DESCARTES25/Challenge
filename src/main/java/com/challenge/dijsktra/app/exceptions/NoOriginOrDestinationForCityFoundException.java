package com.challenge.dijsktra.app.exceptions;

import com.challenge.dijsktra.app.model.City;

public class NoOriginOrDestinationForCityFoundException extends RuntimeException {

    public NoOriginOrDestinationForCityFoundException(City city) {

        super(String.format("No origin or destination found for City: %s" , city.getName()));
    }
}


