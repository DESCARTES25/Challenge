package com.challenge.dijsktra.app.exceptions;


public class DuplicateCityException extends RuntimeException {

    public DuplicateCityException(String name) {

        super(String.format("City with name %s is already in the database", name));
    }
}
