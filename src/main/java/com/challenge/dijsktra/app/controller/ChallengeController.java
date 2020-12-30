package com.challenge.dijsktra.app.controller;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;


import com.challenge.dijsktra.app.SpringBootChallengeDijsktraApplication;
import com.challenge.dijsktra.app.DTOs.CityDTO;
import com.challenge.dijsktra.app.DTOs.ItineraryDTO;
import com.challenge.dijsktra.app.algorithm.Dijkstra;
import com.challenge.dijsktra.app.algorithm.Graph;
import com.challenge.dijsktra.app.algorithm.Node;
import com.challenge.dijsktra.app.exceptions.ItinerariesWithNegativeTimeException;
import com.challenge.dijsktra.app.exceptions.ItinerariesWithZeroTimeException;
import com.challenge.dijsktra.app.exceptions.NoCitiesFoundException;
import com.challenge.dijsktra.app.exceptions.NoOriginOrDestinationForCityFoundException;
import com.challenge.dijsktra.app.exceptions.NoItinerariesFoundException;
import com.challenge.dijsktra.app.model.City;
import com.challenge.dijsktra.app.model.Itinerary;
import com.challenge.dijsktra.app.service.ICityService;
import com.challenge.dijsktra.app.service.IItineraryService;

@RestController
public class ChallengeController {

	private static final Logger log = LoggerFactory.getLogger(ChallengeController.class);
	
	@Autowired
	@Qualifier("com.challenge.dijkstra.app.service.CityService")
	private ICityService cityService;
	@Autowired
	@Qualifier("com.challenge.dijkstra.app.service.ItineraryService")
	private IItineraryService itineraryService;

	@GetMapping("/cities")
	public List<CityDTO> getCities() {

		return getCitiesFromRestDBApi();

	}
	
	@GetMapping("/itineraries")
	public List<ItineraryDTO> getItineraries() {

		return getItinerariesFromRestDBApi();

	}


	@GetMapping("/shortesttime/from/{from}/to/{to}")
	public List<Itinerary> shortestTimeItinerary(@PathVariable String from, @PathVariable String to) {

		// Calculate the shortest time for the path between "from" city and "to" city
		return calculateShortestPath("T", from, to);

	}

	@GetMapping("/shortestconnection/from/{from}/to/{to}")
	public List<Itinerary> shortestConnectionItinerary(@PathVariable String from, @PathVariable String to) {
		// Calculate the shortest number of connections for the path between "from" city
		// and "to" city
		return calculateShortestPath("C", from, to);

	}

	private List<CityDTO> getCitiesFromRestDBApi() {

		RestTemplate restTemplate = new RestTemplate();
		List<CityDTO> cities = Arrays
				.stream(restTemplate.getForObject("http://localhost:8082/api/cities", CityDTO[].class))
				.collect(Collectors.toList());

		return cities;

	}
	
	private List<ItineraryDTO> getItinerariesFromRestDBApi() {

		RestTemplate restTemplate = new RestTemplate();
		List<ItineraryDTO> itineraries = Arrays
				.stream(restTemplate.getForObject("http://localhost:8082/api/itineraries", ItineraryDTO[].class))
				.collect(Collectors.toList());

		return itineraries;
		 
	}

	private List<Itinerary> calculateShortestPath(String timeOrConnections, String from, String to) {

		// Filling Cities and Itineraries from Database API
		fillDataFromDBRestApi();
		// Validates the correctness of data before starting graph calculations
		checkDataErrors();

		// Extracting origin and destination from the PathVariable
		City origin = null;
		City destination = null;
		origin = cityService.findByName(from);
		destination = cityService.findByName(to);

		// Filling Graph Nodes with each City in DB
		List<Node> cityNodes = fillCityNodes();

		// Creating Graph to work out shortest time between cities
		Graph graph = createGraph(timeOrConnections, cityNodes);

		// Calculate the shortest graph from origin to each destination city
		Graph graphFrom = calculateShortestTimeGraph(graph, cityNodes, origin);

		// Prints Shortest Path Between Origin and Destination
		return printShortestGraph(graphFrom, origin, destination);

	}

	private void fillDataFromDBRestApi() {

		// Control if the Data is already filled, avoiding to fill the database several
		// times
		if (cityService.count() == 0 && itineraryService.count() == 0) {

			//Mapping DTO -> Entity and saving it
			//Getting Cities
			ModelMapper modelMapper = new ModelMapper();
			
			List<CityDTO> citiesDB = getCitiesFromRestDBApi();

			for (CityDTO cityDB : citiesDB) {
				City city = modelMapper.map(cityDB, City.class);
				cityService.save(city);
			}
			
			//Getting Itineraries
			List<ItineraryDTO> itinerariesDB = getItinerariesFromRestDBApi();

			for (ItineraryDTO itineraryDB : itinerariesDB) {
				Itinerary itinerary = modelMapper.map(itineraryDB, Itinerary.class);
				itineraryService.save(itinerary);
			}
			
			
		}

	}

	private void checkDataErrors() {

		// Checks cities are filled
		if (cityService.count() == 0) {
			throw new NoCitiesFoundException();
		}

		// Checks itineraries are filled
		if (itineraryService.count() == 0) {
			throw new NoItinerariesFoundException();
		}

		// Checks Itinerary with no time between departure and arrival
		Itinerary itineraryZero = itineraryService.existItinerariesWithZerotime();
		if (itineraryZero != null) {
			throw new ItinerariesWithZeroTimeException(itineraryZero);
		}

		// Checks Itinerary with no time between departure and arrival
		Itinerary itineraryNegative = itineraryService.existItinerariesWithZerotime();
		if (itineraryNegative != null) {
			throw new ItinerariesWithNegativeTimeException(itineraryNegative);
		}

		// Fetch that all cities are destination or origin in at least one itinerary
		for (City city : cityService.findAll()) {
			log.info(city.toString());

			List<Itinerary> originItineraries = itineraryService.findByOrigin(city);
			List<Itinerary> destinationItineraries = itineraryService.findByDestination(city);
			if (originItineraries.isEmpty() && destinationItineraries.isEmpty()) {
				throw new NoOriginOrDestinationForCityFoundException(city);
			}

		}

	}

	private List<Node> fillCityNodes() {

		// Filling Node List with Cities
		List<Node> cityNodes = new ArrayList<Node>();

		for (City cityN : cityService.findAll()) {
			Node node = new Node(cityN);
			cityNodes.add(node);
			log.info(cityN.toString() + " Added to City Nodes");
		}
		return cityNodes;

	}

	private Graph createGraph(String timeOrConnections, List<Node> cityNodes) {

		// Creating ShortestTimeGraph (Cities + Itineraries)

		// Searching for itineraries whose origin node is this city
		Graph graph = new Graph();

		for (Node node : cityNodes) {
			City origin = node.getCity();
			// Searching for itineraries whose origin node is this city
			List<Itinerary> cityItineraries = itineraryService.findByOrigin(origin);
			for (Itinerary itinerary : cityItineraries) {

				Optional<Node> opDestinationNode = cityNodes.stream()
						.filter(p -> p.getCity().getName().equals(itinerary.getDestination().getName())).findFirst();

				try {

					// If a value is present in this Optional, returns the value, otherwise throws
					// NoSuchElementException.
					opDestinationNode.isPresent();
					Node destinationNode = opDestinationNode.get();
					Integer distance = 0;
					if (timeOrConnections.equals("T")) {
						Duration duration = Duration.between(itinerary.getDeparture(), itinerary.getArrival());
						distance = (int) duration.getSeconds();
						log.info("Duration between " + origin.getName() + " and " + itinerary.getDestination().getName()
								+ " is " + distance + " seconds.");
					} else {
						// For Connections the distance between nodes will be "1" to get the
						// ShortestConnection Itinerary
						distance = 1;

					}
					node.addDestination(destinationNode, distance);

				} catch (NoSuchElementException ex) {
					log.info("destination node not found in cityNodes");
				}

			}
			// Adding each node to the graph
			graph.addNode(node);
		}
		return graph;
	}

	private Graph calculateShortestTimeGraph(Graph graph, List<Node> cityNodes, City origin) {

		// Searching for de initial node related with de city origin
		Node originNode = null;

		try {

			Optional<Node> opOriginNode = cityNodes.stream().filter(p -> p.getCity().getName().equals(origin.getName()))
					.findFirst();

			// If a value is present in this Optional, returns the value, otherwise throws
			// NoSuchElementException.
			opOriginNode.isPresent();
			originNode = opOriginNode.get();

		} catch (NoSuchElementException ex) {
			log.info(origin.getName() + " not found in cityNodes");
		}

		// Calculating shortest distance from each node
		Graph graphFrom = Dijkstra.calculateShortestPathFromSource(graph, originNode);

		return graphFrom;
	}

	private List<Itinerary> printShortestGraph(Graph graphFrom, City origin, City endDestination) {
		List<Itinerary> shortestPath = new ArrayList<Itinerary>();
		// Searching for Destination City in
		for (Node node : graphFrom.getNodes()) {
			if (node.getCity().getName().equals(endDestination.getName())) {

				City originCity = null;
				City destinationCity = null;
				for (int i = 0; i <= node.getShortestPath().size() - 1; i++) {

					originCity = node.getShortestPath().get(i).getCity();

					if (node.getShortestPath().size() == i + 1) {
						destinationCity = endDestination;
					} else {
						destinationCity = node.getShortestPath().get(i + 1).getCity();

					}
					Itinerary itinerary = itineraryService.findByOriginAndDestination(originCity, destinationCity);
					shortestPath.add(itinerary);
					/*
					 * log.info(originCity.getName() + " to " + destinationCity.getName() +
					 * " Itinerary: " + itinerary.toString());
					 */

				}

			}

		}
		return shortestPath;

	}

}
