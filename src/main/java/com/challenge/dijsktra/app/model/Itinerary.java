package com.challenge.dijsktra.app.model;

import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table( name = "ITINERARY",
uniqueConstraints = { @UniqueConstraint( columnNames = { "origin_id", "destination_id" } ) } )
public class Itinerary {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@OneToOne
    @JoinColumn(name = "origin_id")
    private City origin;
	@OneToOne
    @JoinColumn(name = "destination_id")
	private City destination;
	private LocalTime departure;
	private LocalTime arrival;

	protected Itinerary() {
	}

	public Itinerary(City origin, City destination, LocalTime departure, LocalTime arrival) {
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

	public City getOrigin() {
		return origin;
	}

	public void setOrigin(City origin) {
		this.origin = origin;
	}

	public City getDestination() {
		return destination;
	}

	public void setDestination(City destination) {
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
