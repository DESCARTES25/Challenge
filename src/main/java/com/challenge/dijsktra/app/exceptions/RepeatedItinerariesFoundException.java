package com.challenge.dijsktra.app.exceptions;

import com.challenge.dijsktra.app.model.Itinerary;

public class RepeatedItinerariesFoundException extends RuntimeException {

    public RepeatedItinerariesFoundException(Itinerary itinerary) {

        super(String.format("Itinerary wirh Origin %s and Destination %s already exists", itinerary.getOrigin().getName(), itinerary.getDestination().getName()));
    }
    
  
}
