package com.challenge.dijsktra.app;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ItineraryRepository extends CrudRepository<Itinerary, Long> {

  List<Itinerary> findAll(); 
	
  List<Itinerary> findByOrigin(City origin);

  List<Itinerary> findByDestination(City destination);
 
  Itinerary findByOriginAndDestination(City origin, City destination);
  
  Itinerary findById(long id);
}