package com.challenge.dijsktra.app;



import com.challenge.dijsktra.app.algorithm.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;


public class DijkstraAlgorithmLongRunningUnitTest {

    @Test
    public void whenDijkstra_HappyPathShortestTime() {

        Node nodeA = new Node("A");
        Node nodeB = new Node("B");
        Node nodeC = new Node("C");
        Node nodeD = new Node("D");
        Node nodeE = new Node("E");
        Node nodeF = new Node("F");

        nodeA.addDestination(nodeB, 10);        
        nodeA.addDestination(nodeC, 15);

        nodeB.addDestination(nodeD, 12);
        nodeB.addDestination(nodeF, 15);

        nodeC.addDestination(nodeE, 10);

        nodeD.addDestination(nodeE, 2);
        nodeD.addDestination(nodeF, 1);

        nodeF.addDestination(nodeE, 5);

        Graph graph = new Graph();

        graph.addNode(nodeA);
        graph.addNode(nodeB);
        graph.addNode(nodeC);
        graph.addNode(nodeD);
        graph.addNode(nodeE);
        graph.addNode(nodeF);

        graph = Dijkstra.calculateShortestPathFromSource(graph, nodeA);

        List<Node> shortestPathForNodeB = Arrays.asList(nodeA);
        List<Node> shortestPathForNodeC = Arrays.asList(nodeA);
        List<Node> shortestPathForNodeD = Arrays.asList(nodeA, nodeB);
        List<Node> shortestPathForNodeE = Arrays.asList(nodeA, nodeB, nodeD);
        List<Node> shortestPathForNodeF = Arrays.asList(nodeA, nodeB, nodeD);

        for (Node node : graph.getNodes()) {
            switch (node.getName()) {
            case "B":
                assertTrue(node
                  .getShortestPath()
                  .equals(shortestPathForNodeB));
                break;
            case "C":
                assertTrue(node
                  .getShortestPath()
                  .equals(shortestPathForNodeC));
                break;
            case "D":
                assertTrue(node
                  .getShortestPath()
                  .equals(shortestPathForNodeD));
                break;
            case "E":
                assertTrue(node
                  .getShortestPath()
                  .equals(shortestPathForNodeE));
                break;
            case "F":
                assertTrue(node
                  .getShortestPath()
                  .equals(shortestPathForNodeF));
                break;
            }
        }
    }

    @Test
    public void whenGraphCycle_Ignore() {

        Node nodeA = new Node("A");
        Node nodeB = new Node("B");
        Node nodeC = new Node("C");
        Node nodeD = new Node("D");
        Node nodeE = new Node("E");
        Node nodeF = new Node("F");

        nodeA.addDestination(nodeB, 10);
        //Graph Cycle
        nodeB.addDestination(nodeA, 10);
        
        nodeA.addDestination(nodeC, 15);

        nodeB.addDestination(nodeD, 12);
        nodeB.addDestination(nodeF, 15);

        nodeC.addDestination(nodeE, 10);
        //Graph Cycle
        nodeE.addDestination(nodeC, 10);
        
        
        nodeD.addDestination(nodeE, 2);
        nodeD.addDestination(nodeF, 1);

        nodeF.addDestination(nodeE, 5);

        Graph graph = new Graph();

        graph.addNode(nodeA);
        graph.addNode(nodeB);
        graph.addNode(nodeC);
        graph.addNode(nodeD);
        graph.addNode(nodeE);
        graph.addNode(nodeF);

        graph = Dijkstra.calculateShortestPathFromSource(graph, nodeA);

        List<Node> shortestPathForNodeB = Arrays.asList(nodeA);
        List<Node> shortestPathForNodeC = Arrays.asList(nodeA);
        List<Node> shortestPathForNodeD = Arrays.asList(nodeA, nodeB);
        List<Node> shortestPathForNodeE = Arrays.asList(nodeA, nodeB, nodeD);
        List<Node> shortestPathForNodeF = Arrays.asList(nodeA, nodeB, nodeD);

        for (Node node : graph.getNodes()) {
            switch (node.getName()) {
            case "B":
                assertTrue(node
                  .getShortestPath()
                  .equals(shortestPathForNodeB));
                break;
            case "C":
                assertTrue(node
                  .getShortestPath()
                  .equals(shortestPathForNodeC));
                break;
            case "D":
                assertTrue(node
                  .getShortestPath()
                  .equals(shortestPathForNodeD));
                break;
            case "E":
                assertTrue(node
                  .getShortestPath()
                  .equals(shortestPathForNodeE));
                break;
            case "F":
                assertTrue(node
                  .getShortestPath()
                  .equals(shortestPathForNodeF));
                break;
            }
        }
    }

    @Test
    public void whenZeroDistance() {

        /*Node nodeA = new Node("A");
        Node nodeB = new Node("B");
        
        nodeA.addDestination(nodeB, 0);
        */
        
               
    }

    @Test
    public void whenDijkstra_HappyPathConnections() {

        Node nodeA = new Node("A");
        Node nodeB = new Node("B");
        Node nodeC = new Node("C");
        Node nodeD = new Node("D");
        Node nodeE = new Node("E");
        Node nodeF = new Node("F");

        nodeA.addDestination(nodeB, 1);
        nodeB.addDestination(nodeA, 1);
        
        nodeA.addDestination(nodeC, 1);

        nodeB.addDestination(nodeD, 1);
        nodeB.addDestination(nodeF, 1);

        nodeC.addDestination(nodeE, 1);

        nodeD.addDestination(nodeE, 1);
        nodeD.addDestination(nodeF, 1);

        nodeF.addDestination(nodeE, 1);

        Graph graph = new Graph();

        graph.addNode(nodeA);
        graph.addNode(nodeB);
        graph.addNode(nodeC);
        graph.addNode(nodeD);
        graph.addNode(nodeE);
        graph.addNode(nodeF);

        graph = Dijkstra.calculateShortestPathFromSource(graph, nodeA);

        List<Node> shortestPathForNodeB = Arrays.asList(nodeA);
        List<Node> shortestPathForNodeC = Arrays.asList(nodeA);
        List<Node> shortestPathForNodeD = Arrays.asList(nodeA, nodeB);
        List<Node> shortestPathForNodeE = Arrays.asList(nodeA, nodeC);
        List<Node> shortestPathForNodeF = Arrays.asList(nodeA, nodeB);

        for (Node node : graph.getNodes()) {
            switch (node.getName()) {
            case "B":
                assertTrue(node
                  .getShortestPath()
                  .equals(shortestPathForNodeB));
                break;
            case "C":
                assertTrue(node
                  .getShortestPath()
                  .equals(shortestPathForNodeC));
                break;
            case "D":
                assertTrue(node
                  .getShortestPath()
                  .equals(shortestPathForNodeD));
                break;
            case "E":
                assertTrue(node
                  .getShortestPath()
                  .equals(shortestPathForNodeE));
                break;
            case "F":
                assertTrue(node
                  .getShortestPath()
                  .equals(shortestPathForNodeF));
                break;
            }
        }
    }


    
}