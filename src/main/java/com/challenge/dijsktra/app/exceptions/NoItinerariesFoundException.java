package com.challenge.dijsktra.app.exceptions;

public class NoItinerariesFoundException extends RuntimeException {

    public NoItinerariesFoundException() {

        super("No Itineraries found");
    }
}
