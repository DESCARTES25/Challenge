package com.challenge.dijsktra.app.exceptions;

public class NoCitiesFoundException extends RuntimeException {

    public NoCitiesFoundException() {

        super("No Cities found");
    }
}


