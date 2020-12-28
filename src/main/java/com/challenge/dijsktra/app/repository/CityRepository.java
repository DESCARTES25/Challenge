package com.challenge.dijsktra.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.challenge.dijsktra.app.model.City;

public interface CityRepository extends CrudRepository<City, Long> {


	
  //Counting Cities in the DB
  long count();		
  
  //Finding City by Name
  Optional<City> findByName(String name);

  //Finding City by Id
  Optional<City> findById(long id);
  
  //Returns a list with all the cities in
  List<City> findAll();
  
  //Checks if exists a city with a name
  boolean existsByName(String name);
}