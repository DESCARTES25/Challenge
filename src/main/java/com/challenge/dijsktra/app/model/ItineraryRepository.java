package com.challenge.dijsktra.app.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ItineraryRepository extends CrudRepository<Itinerary, Long> {

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
}