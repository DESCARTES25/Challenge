package com.challenge.dijsktra.app.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface CityRepository extends CrudRepository<City, Long> {

  //Counting Cities in the DB
  long count();		
  
  //Finding City by Name
  City findByName(String name);

  //Finding City by Id
  City findById(long id);
}