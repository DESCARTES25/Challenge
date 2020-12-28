package com.challenge.dijsktra.app.exceptions;

import com.challenge.dijsktra.app.model.Itinerary;

public class ItinerariesWithNegativeTimeException extends RuntimeException {

    public ItinerariesWithNegativeTimeException(Itinerary itinerary) {

        super(String.format("Itinerary Id %d has the an arrival time earlier thab departure time", itinerary.getId()));
    }
    
  
}
