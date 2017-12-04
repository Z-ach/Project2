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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class DirectedGraph<T> implements GraphInterface<T> {

	private Map<T, VertexInterface<T>> vertices;
	private int edgeCount;

	public DirectedGraph() {
		vertices = new HashMap<>();
		edgeCount = 0;
	}

	public boolean addVertex(T vertexLabel) {
		VertexInterface<T> addOutcome = vertices.put(vertexLabel, new Vertex<>(vertexLabel));
		return addOutcome == null;
	}

	public boolean addEdge(T begin, T end, double edgeWeight) {
		boolean result = false;
		VertexInterface<T> beginVertex = vertices.get(begin);
		VertexInterface<T> endVertex = vertices.get(end);
		if ((beginVertex != null) && (endVertex != null))
			result = beginVertex.connect(endVertex, edgeWeight);
		if (result)
			edgeCount++;
		return result;
	}

	public boolean addEdge(T begin, T end) {
		return addEdge(begin, end, 0);
	}

	@Override
	public boolean hasEdge(T begin, T end) {
		boolean result = false;
		VertexInterface<T> originVertex = vertices.get(begin);
		VertexInterface<T> endVertex = vertices.get(end);
		if (originVertex != null && endVertex != null) {
			Iterator<VertexInterface<T>> neighbors = originVertex.getNeighborIterator();
			while (neighbors.hasNext() && !result) {
				VertexInterface<T> nextNeighbor = neighbors.next();
				if (nextNeighbor.equals(endVertex))
					result = true;
			}
		}
		return result;
	}

	@Override
	public boolean isEmpty() {
		return vertices.isEmpty();
	}

	@Override
	public int getNumberOfVertices() {
		return vertices.size();
	}

	@Override
	public int getNumberOfEdges() {
		return edgeCount;
	}

	@Override
	public void clear() {
		vertices.clear();
		edgeCount = 0;
	}

	@Override
	public Queue<T> getBreadthFirstTraversal(T origin) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Queue<T> getDepthFirstTraversal(T origin) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Stack<T> getTopologicalOrder() {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getShortestPath(T begin, T end, Stack<T> path) {
		throw new UnsupportedOperationException();
	}

	@Override
	public double getCheapestPath(T begin, T end, Stack<T> path) {
		resetVertices();
		boolean done = false;
		double pathCost = 0;
		PriorityQueue<PriorityQueueEntry> vertexQueue = new PriorityQueue<PriorityQueueEntry>();
		VertexInterface<T> endVertex = null;
		vertexQueue.add(new PriorityQueueEntry(vertices.get(begin), 0, null));
		while (!done && !vertexQueue.isEmpty()) {
			PriorityQueueEntry frontEntry = vertexQueue.poll();
			VertexInterface<T> frontVertex = frontEntry.getVertex();
			if (!frontVertex.isVisited()) {
				frontVertex.visit();
				frontVertex.setCost(frontEntry.getCost());
				frontVertex.setPredecessor(frontEntry.getPredecessor());
				if (frontVertex.equals(vertices.get(end))) {
					endVertex = frontVertex;
					pathCost = frontEntry.getCost();
					done = true;
				} else {
					Iterator<VertexInterface<T>> neighbors = frontVertex.getNeighborIterator();
					Iterator<Double> weights = frontVertex.getWeightIterator();
					while (neighbors.hasNext()) {
						VertexInterface<T> nextNeighbor = neighbors.next();
						double weightOfEdgeToNeighbor = weights.next();
						if (!nextNeighbor.isVisited()) {
							double nextCost = weightOfEdgeToNeighbor + frontEntry.getCost();
							vertexQueue.add(new PriorityQueueEntry(nextNeighbor, nextCost, frontVertex));
						}
					}
				}
			}
		}

		if (endVertex != null) {
			path.push(endVertex.getLabel());
			VertexInterface<T> vertex = endVertex;
			while (vertex.hasPredecessor()) {
				vertex = vertex.getPredecessor();
				path.push(vertex.getLabel());
			}
		}

		return pathCost;
	}

	protected void resetVertices() {
		Iterator<VertexInterface<T>> vertexIterator = vertices.values().iterator();
		while (vertexIterator.hasNext()) {
			VertexInterface<T> nextVertex = vertexIterator.next();
			nextVertex.unvisit();
			nextVertex.setCost(0);
			nextVertex.setPredecessor(null);
		}
	}

	private class PriorityQueueEntry implements Comparable<T> {
		private VertexInterface<T> vertex, predecessor;
		private double cost;

		private PriorityQueueEntry(VertexInterface<T> currentVertex, double cost, VertexInterface<T> previousVertex) {
			vertex = currentVertex;
			this.cost = cost;
			predecessor = previousVertex;
		}

		public VertexInterface<T> getVertex() {
			return vertex;
		}

		public VertexInterface<T> getPredecessor() {
			return predecessor;
		}

		public double getCost() {
			return cost;
		}

		@SuppressWarnings("unchecked")
		public int compareTo(Object other) {
			return (int) (this.cost - ((PriorityQueueEntry) other).getCost());
		}

	}
}
