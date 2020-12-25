package com.challenge.dijsktra.app;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.challenge.dijsktra.app.algorithm.*;

@SpringBootApplication
public class SpringBootChallengeDijsktraApplication {

	private static final Logger log = LoggerFactory.getLogger(SpringBootChallengeDijsktraApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringBootChallengeDijsktraApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(CityRepository repository, ItineraryRepository itRepository) {
		return (args) -> {
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

			// Creating ShortestTimeGraph (Cities + Itineraries)

			// Filling Node List with Cities
			List<Node> cityNodes = new ArrayList<Node>();

			for (City cityN : repository.findAll()) {
				Node node = new Node(cityN.getName());
				cityNodes.add(node);
				log.info(cityN.toString() + " Added to City Nodes");
			}

			// Searching for itineraries whose origin node is this city
			Graph graph = new Graph();

			for (Node node : cityNodes) {
				City origin = repository.findByName(node.getName());
				// Searching for itineraries whose origin node is this city
				List<Itinerary> cityItineraries = itRepository.findByOrigin(origin);
				for (Itinerary itinerary : cityItineraries) {

					Optional<Node> opDestinationNode = cityNodes.stream()
							.filter(p -> p.getName().equals(itinerary.getDestination().getName())).findFirst();

					try {

						// If a value is present in this Optional, returns the value, otherwise throws
						// NoSuchElementException.
						opDestinationNode.isPresent();
						Node destinationNode = opDestinationNode.get();
						Duration duration = Duration.between(itinerary.getDeparture(), itinerary.getArrival());
						Integer distance = (int) duration.getSeconds();
						log.info("Duration between %s and %s  is  %s seconds.%n", origin.getName(),
								itinerary.getDestination().getName(), distance);

						node.addDestination(destinationNode, distance);

					} catch (NoSuchElementException ex) {
						log.info("destination node not found in cityNodes");
					}

				}
				// Adding each node to the graph
				graph.addNode(node);
			}

			// Calculating shortest distance from each node
			Graph graphfromMadrid = Dijkstra.calculateShortestPathFromSource(graph, cityNodes.get(0));

			for (Node node : graphfromMadrid.getNodes()) {
				switch (node.getName()) {
				case "Berlin":
					log.info("Shortest Path from " + cityNodes.get(0).getName() + " to Berlin: ");
					node.getShortestPath().forEach(cityL -> {
						log.info(cityL.getName());
					});

					break;
				case "Tokyo":
					log.info("Shortest Path from " + cityNodes.get(0).getName() + " to Tokyo: ");
					node.getShortestPath().forEach(cityL -> {
						log.info(cityL.getName());
					});

					break;
				
				case "New York":
					log.info("Shortest Path from " + cityNodes.get(0).getName() + " to New York: ");
					node.getShortestPath().forEach(cityL -> {
						log.info(cityL.getName());
					});
	
					break;
				case "Londres":
					log.info("Shortest Path from " + cityNodes.get(0).getName() + " to Londres: ");
					node.getShortestPath().forEach(cityL -> {
						log.info(cityL.getName());
					});
	
					break;	
				
				case "Paris":
					log.info("Shortest Path from " + cityNodes.get(0).getName() + " to Paris: ");
					node.getShortestPath().forEach(cityL -> {
						log.info(cityL.getName());
					});
	
					break;	
				}

			}

		};
	}
}