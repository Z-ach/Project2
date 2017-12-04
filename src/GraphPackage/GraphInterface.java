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
/**
   An interface of methods that create, manipulate, and process a graph.
   
   @author Frank M. Carrano
   @author Timothy M. Henry
   @version 4.0
*/
public interface GraphInterface<T> extends BasicGraphInterface<T>, 
                                           GraphAlgorithmsInterface<T>
{
} // end GraphInterface