//
// Name: Kaufman, Zach
// Project: #2
// Due: 12/4/2017
// Course: cs-241-02-f17
//
// Description:
// Implement a graph data structure and use it to find the cheapest path of a graph
// that is created by reading in city names (vertices) and roads (edges) from a text
// file named "map.txt". Print out all cities along the cheapest path, and list the
// distance (the cost of the path) after the cities.
//
package GraphPackage;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class Vertex<T> implements VertexInterface<T>{
	
	private T label;
	private LinkedList<Edge> edgeList;
	private boolean visited;
	private VertexInterface<T> previousVertex;
	private double cost;
	
	public Vertex(T vertexLabel) {
		label = vertexLabel;
		edgeList = new LinkedList<>();
		visited = false;
		previousVertex = null;
		cost = 0;
	}

	public T getLabel() {
		return label;
	}

	public void visit() {
		visited = true;
	}

	public void unvisit() {
		visited = false;
	}

	public boolean isVisited() {
		return visited;
	}

	public boolean connect(VertexInterface<T> endVertex, double edgeWeight) {
		boolean result = false;
		
		if(!this.equals(endVertex)) {
			Iterator<VertexInterface<T>> neighbors = getNeighborIterator();
			boolean duplicateEdge = false;
			while(!duplicateEdge && neighbors.hasNext()) {
				VertexInterface<T> nextNeighbor = neighbors.next();
				if(endVertex.equals(nextNeighbor))
					duplicateEdge = true;
			}
			if(!duplicateEdge) {
				edgeList.add(new Edge(endVertex, edgeWeight));
				result = true;
			}
		}
		
		return result;
	}

	public boolean connect(VertexInterface<T> endVertex) {
		return connect(endVertex, 0);
	}

	public Iterator<VertexInterface<T>> getNeighborIterator() {
		return new NeighborIterator();
	}

	public Iterator<Double> getWeightIterator() {
		return new WeightIterator();
	}

	public boolean hasNeighbor() {
		return !edgeList.isEmpty();
	}

	public VertexInterface<T> getUnvisitedNeighbor() {
		VertexInterface<T> result = null;
		Iterator<VertexInterface<T>> neighbors = getNeighborIterator();
		if(neighbors.hasNext() && (result == null)) {
			VertexInterface<T> nextNeighbor = neighbors.next();
			if(!nextNeighbor.isVisited())
				result = nextNeighbor;
		}
		return result;
	}

	public void setPredecessor(VertexInterface<T> predecessor) {
		this.previousVertex = predecessor;
	}

	public VertexInterface<T> getPredecessor() {
		return previousVertex;
	}

	public boolean hasPredecessor() {
		return previousVertex != null;
	}

	public void setCost(double newCost) {
		cost = newCost;
	}

	public double getCost() {
		return cost;
	}
	
	public boolean equals(Object other) {
		boolean result;
		if((other == null) || (getClass() != other.getClass()))
			result = false;
		else {
			@SuppressWarnings("unchecked")
			Vertex<T> otherVertex = (Vertex<T>)other;
			result = label.equals(otherVertex.label);
		}
		return result;
	}
	
	protected class Edge{
		private VertexInterface<T> vertex;
		private double weight;
		
		protected Edge(VertexInterface<T> endVertex, double edgeWeight) {
			vertex = endVertex;
			weight = edgeWeight;
		}
		
		protected VertexInterface<T> getEndVertex(){
			return vertex;
		}
		
		protected double getWeight() {
			return weight;
		}
		
	}
	
	private class NeighborIterator implements Iterator<VertexInterface<T>>{
		
		private Iterator<Edge> edges;
		
		private NeighborIterator() {
			edges = edgeList.iterator();
		}
		
		public boolean hasNext() {
			return edges.hasNext();
		}

		public VertexInterface<T> next() {
			VertexInterface<T> nextNeighbor = null;
			if(edges.hasNext()) {
				Edge edgeToNextNeighbor = edges.next();
				nextNeighbor = edgeToNextNeighbor.getEndVertex();
			}
			else
				throw new NoSuchElementException();
			
			return nextNeighbor;
		}
		
	}
	
	private class WeightIterator implements Iterator<Double>{
		
		private Iterator<Edge> edges;
		
		private WeightIterator() {
			edges = edgeList.iterator();
		}
		
		public boolean hasNext() {
			return edges.hasNext();
		}

		public Double next() {
			double nextWeight = 0;
			if(edges.hasNext()) {
				Edge edgeToNextNeighbor = edges.next();
				nextWeight = edgeToNextNeighbor.getWeight();
			}
			else
				throw new NoSuchElementException();
			
			return nextWeight;
		}
		
	}

	
}
