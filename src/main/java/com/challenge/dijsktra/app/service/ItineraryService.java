package com.challenge.dijsktra.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.challenge.dijsktra.app.exceptions.CityNotFoundException;
import com.challenge.dijsktra.app.exceptions.ItineraryNotFoundException;
import com.challenge.dijsktra.app.exceptions.NoItinerariesFoundException;
import com.challenge.dijsktra.app.exceptions.RepeatedItinerariesFoundException;
import com.challenge.dijsktra.app.model.City;
import com.challenge.dijsktra.app.model.Itinerary;
import com.challenge.dijsktra.app.repository.CityRepository;
import com.challenge.dijsktra.app.repository.ItineraryRepository;

@Service
@Qualifier("com.challenge.dijkstra.app.service.ItineraryService")
public class ItineraryService implements IItineraryService {

	@Autowired
	private ItineraryRepository itineraryRepository;

	@Override
	public Itinerary save(Itinerary itinerary) {

		Itinerary it =  findByOriginAndDestination(itinerary.getOrigin(), itinerary.getDestination());
		//Check if it is an Itinerary with the same origin and destination, in that case show an error
		if (it != null) {
			throw new RepeatedItinerariesFoundException(it);
		}
		return itineraryRepository.save(itinerary);
	}

	@Override
	public long count() {
		return itineraryRepository.count();
	}

	@Override
	public List<Itinerary> findAll() {

		var itineraries = (List<Itinerary>) itineraryRepository.findAll();

		if (itineraries.isEmpty()) {

			throw new NoItinerariesFoundException();
		}

		return itineraries;

	}

	@Override
	public List<Itinerary> findByOrigin(City origin) {
		return itineraryRepository.findByOrigin(origin);
	}

	@Override
	public List<Itinerary> findByDestination(City destination) {
		return itineraryRepository.findByDestination(destination);
	}

	@Override
	public Itinerary findByOriginAndDestination(City origin, City destination) {
		return itineraryRepository.findByOriginAndDestination(origin, destination);
	}
	
	@Override
	public Itinerary findById(long id) {

		Optional<Itinerary> op = itineraryRepository.findById(id);
		return op.orElseThrow(() -> new ItineraryNotFoundException(id));

	}

	@Override
	public Itinerary existItinerariesWithZerotime() {
		// Fetch all itineraries
		for (Itinerary itinerary : itineraryRepository.findAll()) {
			// If arrival and departure time are the same, it should be an error
			if (itinerary.getArrival() == itinerary.getDeparture()) {
				return itinerary;
			}

		}
		return null;
	}

	@Override
	public Itinerary existItinerariesWithNegativetime() {
		// Fetch all itineraries
		for (Itinerary itinerary : itineraryRepository.findAll()) {
			// If arrival time is before departure time, it should be an error
			if (itinerary.getArrival().compareTo(itinerary.getDeparture()) < 0) {
				return itinerary;
			}

		}
		return null;
	}

}
