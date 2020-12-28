package com.challenge.dijsktra.app.service;

import java.util.List;

import com.challenge.dijsktra.app.model.City;

public interface ICityService {
	
	  //Creates a new city	
	  City save(City city);
	
	  //Counting Cities in the DB
	  long count();		
	  
	  //Finding City by Name
	  City findByName(String name);

	  //Finding City by Id
	  City findById(long id);
	  
	  //Return all the cities in DB
	  List<City> findAll();
	  
	  //Checks if exists a city with this name
	  boolean existsByName(String name);
}
