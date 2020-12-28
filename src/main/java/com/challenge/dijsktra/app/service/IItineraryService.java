package com.challenge.dijsktra.app.service;

import java.util.List;

import com.challenge.dijsktra.app.model.City;
import com.challenge.dijsktra.app.model.Itinerary;

public interface IItineraryService {

	  //Creates new Itenerary
	  Itinerary save(Itinerary itinerary);
    
	  //Counting Itineraries in the DB
	  long count();	

	  //Returns all itineraries in DB
	  List<Itinerary> findAll(); 
		
	  //Returns a list of itineraries where origin is the city we pass as parameter 
	  List<Itinerary> findByOrigin(City origin);

	  //Returns a list of itineraries where destination is the city we pass as parameter 
	  List<Itinerary> findByDestination(City destination);
	 
	  //Returns a list of itineraries where origin and destination are the cities we pass as parameters 
	  Itinerary findByOriginAndDestination(City origin, City destination);
	  
	
	  //Returns a Itinerary by its Id
	  Itinerary findById(long id);
	  
	  //Checks Itinerary with no time between departure and arrival
	  Itinerary existItinerariesWithZerotime();
	  
	  //Checks Itinerary with negative time between departure and arrival
	  Itinerary existItinerariesWithNegativetime();
	
}
