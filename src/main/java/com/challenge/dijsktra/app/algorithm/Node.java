package com.challenge.dijsktra.app.algorithm;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.challenge.dijsktra.app.City;
import com.challenge.dijsktra.app.Itinerary;

public class Node {

    private City  city;

    private List<Itinerary>  itinerariesFromCity = new LinkedList<>();
    
    private LinkedList<Node> shortestPath = new LinkedList<>();

    private Integer distance = Integer.MAX_VALUE;

    private Map<Node, Integer> adjacentNodes = new HashMap<>();

    public Node(City city) {
        this.setCity(city);
    }

    public void addDestination(Node destination, int distance, Itinerary itinerary ) {
    	if (distance == 0) { 
    		//To_do Launch Exception (Distance between nodes cannot be 0 
    	}else {
    		itinerariesFromCity.add(itinerary);
    		adjacentNodes.put(destination, distance);
    	}
    }

   
    public Map<Node, Integer> getAdjacentNodes() {
        return adjacentNodes;
    }

    public void setAdjacentNodes(Map<Node, Integer> adjacentNodes) {
        this.adjacentNodes = adjacentNodes;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public List<Node> getShortestPath() {
        return shortestPath;
    }

    public void setShortestPath(LinkedList<Node> shortestPath) {
        this.shortestPath = shortestPath;
    }

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public List<Itinerary> getItinerariesFromCity() {
		return itinerariesFromCity;
	}

	public void setItinerariesFromCity(List<Itinerary> itinerariesFromCity) {
		this.itinerariesFromCity = itinerariesFromCity;
	}

}
