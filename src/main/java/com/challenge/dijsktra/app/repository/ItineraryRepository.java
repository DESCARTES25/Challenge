package com.challenge.dijsktra.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.challenge.dijsktra.app.model.City;
import com.challenge.dijsktra.app.model.Itinerary;

@Repository
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
  Optional<Itinerary> findById(long id);
}