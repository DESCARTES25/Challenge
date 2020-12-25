package com.challenge.dijsktra.app;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface CityRepository extends CrudRepository<City, Long> {

  City findByName(String name);

  City findById(long id);
}