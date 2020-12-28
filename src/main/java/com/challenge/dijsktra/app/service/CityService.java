package com.challenge.dijsktra.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.challenge.dijsktra.app.exceptions.CityNotFoundException;
import com.challenge.dijsktra.app.exceptions.DuplicateCityException;
import com.challenge.dijsktra.app.exceptions.NoCitiesFoundException;
import com.challenge.dijsktra.app.exceptions.NoItinerariesFoundException;
import com.challenge.dijsktra.app.model.City;
import com.challenge.dijsktra.app.model.Itinerary;
import com.challenge.dijsktra.app.repository.CityRepository;


@Service
@Qualifier("com.challenge.dijkstra.app.service.CityService")
public class CityService implements ICityService {

	/*@Autowired
	private CityRepository cityRepository;
*/
	private final CityRepository cityRepository;
	
	public CityService(CityRepository cityRepository) {
		this.cityRepository = cityRepository;
		
	}
	@Override
    public City save(City city) {
		//Checks duplicate Cities and returns a controlled exception
		if (cityRepository.existsByName(city.getName())){
			throw new DuplicateCityException(city.getName());
		}
		return cityRepository.save(city);
    }
	
	@Override
	public long count() {
		return cityRepository.count();
	}
	
	@Override
	public List<City> findAll() {
		
		var cities = (List<City>) cityRepository.findAll();

		if (cities.isEmpty()) {

			throw new NoCitiesFoundException();
		}

		return cities;

	}

	@Override
	public City findByName(String name) {
		Optional<City> op = cityRepository.findByName(name);
		return op.orElseThrow(() -> new CityNotFoundException(name));
	}

	@Override
	public City findById(long id) {
		Optional<City> op = cityRepository.findById(id);
		return op.orElseThrow(() -> new CityNotFoundException(id));
	}

	@Override
	public boolean existsByName(String name) {
		return cityRepository.existsByName(name);
	}
}