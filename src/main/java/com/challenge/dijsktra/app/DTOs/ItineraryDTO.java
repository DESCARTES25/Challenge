package com.challenge.dijsktra.app.DTOs;

import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

public class ItineraryDTO {


	private Long id;

    private CityDTO origin;

	private CityDTO destination;
	private LocalTime departure;
	private LocalTime arrival;

	protected ItineraryDTO() {
	}

	public ItineraryDTO(CityDTO origin, CityDTO destination, LocalTime departure, LocalTime arrival) {
		this.setOrigin(origin);
		this.setDestination(destination);
		this.setDeparture(departure);
		this.setArrival(arrival);
	}

	@Override
	public String toString() {
		return String.format("Itinerary [id=%d, Origin='%s', Destination='%s', Departure='%s', Arrival='%s']", id,
				origin.getName(), destination.getName(), getDeparture().toString(), getArrival().toString());
	}

	public Long getId() {
		return id;
	}

	public CityDTO getOrigin() {
		return origin;
	}

	public void setOrigin(CityDTO origin) {
		this.origin = origin;
	}

	public CityDTO getDestination() {
		return destination;
	}

	public void setDestination(CityDTO destination) {
		this.destination = destination;
	}

	public LocalTime getDeparture() {
		return departure;
	}

	public void setDeparture(LocalTime departure) {
		this.departure = departure;
	}

	public LocalTime getArrival() {
		return arrival;
	}

	public void setArrival(LocalTime arrival) {
		this.arrival = arrival;
	}

}
