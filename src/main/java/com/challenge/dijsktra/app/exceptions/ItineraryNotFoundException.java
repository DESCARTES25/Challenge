package com.challenge.dijsktra.app.exceptions;


public class ItineraryNotFoundException extends RuntimeException {

    public ItineraryNotFoundException(Long id) {

        super(String.format("Itinerary with Id %d not found", id));
    }
    
  
}
