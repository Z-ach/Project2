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
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

import GraphPackage.DirectedGraph;
import GraphPackage.GraphInterface;

public class MyMap {

	public static void main(String[] args) {
		System.out.println("Z. Kaufman's MyMap\n");

		// Create a GraphGenerator object to deal with reading in map.txt and creating a new graph
		GraphGenerator graphMaker = new GraphGenerator();

		// Use a HashMap to store the city IDs and city names together, this makes accessing them easy
		Map<Integer, String> cityIDs = new HashMap<Integer, String>();

		// Create a new graph using graphMaker, store the city ID / city Name pairs in the HashMap made earlier
		GraphInterface<String> cityMap = graphMaker.generate(cityIDs);

		// Create a new stack to hold the cheapest path generated
		Stack<String> cheapestPath = new Stack<String>();

		// Ensure that the arguments provided are the right length, and exist as values in the HashMap
		if (args.length == 2) {
			if (cityIDs.containsValue(args[0]) && cityIDs.containsValue(args[1])) {

				// Run the cheapest path algorithm, store the distance in a double and the pathin the stack created earlier
				double distance = cityMap.getCheapestPath(args[0], args[1], cheapestPath);

				// Ensure that a path was found
				if (distance != 0) {
					// Go through cheapest path and print out city names along the path
					while (!cheapestPath.isEmpty()) {
						System.out.println(cheapestPath.pop());
					}
					System.out.println("Distance = " + distance);
				} else // Distance = 0, which means no path was found
					System.out.println("No path found.");
			} else {// One of the cities entered was not a valid city name, use if/else to figure out which one was invalid
				if (!cityIDs.containsValue(args[0]))
					System.out.println(args[0] + " not found.");
				else
					System.out.println(args[1] + " not found.");
			}
		}else {//if number of arguments is incorrect, specify correct usage
			System.out.println("usage: MyMap fromCity$ toCity$");
		}

	}
}

class GraphGenerator {

	protected GraphInterface<String> generate(Map<Integer, String> cityIDs) {
		// Create a new file to access map.txt
		File mapFile = new File("map.txt");

		// Create a new scanner object to access lines within the file
		Scanner fileReader = null;
		try {
			fileReader = new Scanner(mapFile);
		} catch (FileNotFoundException e) {
			System.err.println("No map file exists.");
			System.exit(0);
		}
		// Create a new graph to represent the cities a roads as verticies and edges
		GraphInterface<String> cityMap = new DirectedGraph<String>();

		// Iterate through the file, create new cities and edges from it, add city IDs/city names to HashMap
		while (fileReader.hasNextLine()) {
			switch (fileReader.next()) {
			case "city":
				Integer id = fileReader.nextInt();
				String cityName = fileReader.next();
				cityIDs.put(id, cityName);
				cityMap.addVertex(cityName);
				break;
			case "road":
				Integer origin = fileReader.nextInt();
				Integer end = fileReader.nextInt();
				Integer weight = fileReader.nextInt();
				cityMap.addEdge(cityIDs.get(origin), cityIDs.get(end), weight);
				break;
			}
		}

		return cityMap;
	}

}
