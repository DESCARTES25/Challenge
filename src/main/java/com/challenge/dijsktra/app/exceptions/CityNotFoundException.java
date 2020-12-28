package com.challenge.dijsktra.app.exceptions;


public class CityNotFoundException extends RuntimeException {

    public CityNotFoundException(Long id) {

        super(String.format("City with Id %d not found", id));
    }
    
    public CityNotFoundException(String name) {

        super(String.format("City with name %s not found", name));
    }
}
