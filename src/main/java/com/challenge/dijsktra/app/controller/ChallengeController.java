package com.challenge.dijsktra.app.controller;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.dijsktra.app.SpringBootChallengeDijsktraApplication;
import com.challenge.dijsktra.app.algorithm.Dijkstra;
import com.challenge.dijsktra.app.algorithm.Graph;
import com.challenge.dijsktra.app.algorithm.Node;
import com.challenge.dijsktra.app.model.City;
import com.challenge.dijsktra.app.model.CityRepository;
import com.challenge.dijsktra.app.model.Itinerary;
import com.challenge.dijsktra.app.model.ItineraryRepository;

@RestController
public class ChallengeController {

	private static final Logger log = LoggerFactory.getLogger(ChallengeController.class);

	@Autowired
	private CityRepository repository;

	@Autowired
	private ItineraryRepository itRepository;

	@GetMapping("/shortesttime/from/{from}/to/{to}")
	public void shortestTimeItinerary(@PathVariable String from, @PathVariable String to) {

		// Filling Cities and Itineraries with Database
		fillDB();

		// Extracting origin and destination from the PathVariable
		City origin = repository.findByName(from);

		City destination = repository.findByName(to);

		// Filling Graph Nodes with each City in DB
		List<Node> cityNodes = fillCityNodes();

		// Creating Graph to work out shortest time between cities
		Graph graph = createGraph("T", cityNodes);

		// Calculate the shortest graph from origin to each destination city
		Graph graphFrom = calculateShortestTimeGraph(graph, cityNodes, origin);

		// Prints Shortest Path Between Origin and Destination
		printShortestTimeGraph(graphFrom, origin, destination);

	}

	@GetMapping("/shortestconnection/from/{from}/to/{to}")
	public void shortestConnectionItinerary(@PathVariable String from, @PathVariable String to) {

		// Filling Cities and Itineraries with Database
		fillDB();

		// Extracting origin and destination from the PathVariable
		City origin = repository.findByName(from);

		City destination = repository.findByName(to);

		// Filling Graph Nodes with each City in DB
		List<Node> cityNodes = fillCityNodes();

		// Creating Graph to work out shortest time between cities
		Graph graph = createGraph("C", cityNodes);

		// Calculate the shortest graph from origin to each destination city
		Graph graphFrom = calculateShortestTimeGraph(graph, cityNodes, origin);

		// Prints Shortest Path Between Origin and Destination
		printShortestTimeGraph(graphFrom, origin, destination);
	}

	private void fillDB() {

		// Control if the DB is already filled

		if (repository.count() == 0 && itRepository.count() == 0) {

			// Save a few cities

			City madrid = new City("Madrid");
			City londres = new City("Londres");
			City berlin = new City("Berlin");
			City tokyo = new City("Tokyo");
			City paris = new City("Paris");
			City newYork = new City("New York");

			repository.save(madrid);
			repository.save(londres);
			repository.save(berlin);
			repository.save(tokyo);
			repository.save(paris);
			repository.save(newYork);

			// Fetch all cities
			log.info("Cities found with findAll():");
			log.info("-------------------------------");
			for (City city : repository.findAll()) {
				log.info(city.toString());
			}
			log.info("");

			// Fetch an individual city by ID
			City city = repository.findById(4L);
			log.info("City found with findById(4L):");
			log.info("--------------------------------");
			log.info(city.toString());
			log.info("");

			// Fetch cities by name
			log.info("City found with findByLastName('Berlin'):");
			log.info("--------------------------------------------");
			City bauer = repository.findByName("Berlin");
			bauer.toString();

			// Save a few itineraries
			itRepository.save(new Itinerary(madrid, berlin, LocalTime.of(0, 0), LocalTime.of(1, 0)));
			itRepository.save(new Itinerary(madrid, paris, LocalTime.of(0, 0), LocalTime.of(2, 0)));
			itRepository.save(new Itinerary(paris, londres, LocalTime.of(1, 0), LocalTime.of(3, 0)));
			itRepository.save(new Itinerary(paris, newYork, LocalTime.of(1, 0), LocalTime.of(5, 0)));
			itRepository.save(new Itinerary(berlin, tokyo, LocalTime.of(6, 0), LocalTime.of(7, 0)));
			itRepository.save(new Itinerary(londres, tokyo, LocalTime.of(10, 0), LocalTime.of(12, 0)));
			itRepository.save(new Itinerary(newYork, tokyo, LocalTime.of(15, 0), LocalTime.of(20, 0)));

			// Fetch all itineraries
			log.info("Cities found with findAll():");
			log.info("-------------------------------");
			for (Itinerary itinerary : itRepository.findAll()) {
				log.info(itinerary.toString());
			}
			log.info("");

		}

	}

	private List<Node> fillCityNodes() {

		// Filling Node List with Cities
		List<Node> cityNodes = new ArrayList<Node>();

		for (City cityN : repository.findAll()) {
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
			List<Itinerary> cityItineraries = itRepository.findByOrigin(origin);
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

	private void printShortestTimeGraph(Graph graphFrom, City origin, City endDestination) {

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
					Itinerary itinerary = itRepository.findByOriginAndDestination(originCity, destinationCity);
					log.info(originCity.getName() + " to " + destinationCity.getName() + " Itinerary: "
							+ itinerary.toString());

				}

			}

		}

	}

}
