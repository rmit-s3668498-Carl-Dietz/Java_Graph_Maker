package graph;

import java.io.PrintWriter;

public interface MyGraph
{
	/**
	 * Adds a vertex to the graph.  If the vertex already exists in the graph, no changes are made.
	 *
	 * @param vertLabel Vertex to add.
	 */
	public abstract void addVertex(String vertLabel);
	
	/**
	 * Removes a vertex from the graph.  If the vertex doesn't exists in the graph, no changes are made, but a warning to System.err should be issued.
	 *
	 * @param vertLabel Vertex to remove.
	 */
	public abstract void removeVertex(String vertLabel);
	
	/**
	 * Adds an edge to the graph.  If the edge already exists in the graph, no changes are made.  If one of the vertices doesn't exist, a warning to System.err should be issued 
	 * and no edge or vertices should be added.
	 *
	 * @param srcLabel Source vertex of edge to add.
	 * @param tarLabel Target vertex of edge to add.
	 */
	public abstract void addEdge(String srcLabel, String tarLabel);
	
	/**
	 * Removes an edge from the graph.  If the edge doesn't exists in the graph, no changes are made, but a warning to System.err should be issued.  If one of the 
	 * vertices doesn't exist, a warning to System.err should be issued and no edge or vertices should be added.
	 *
	 * @param srcLabel Source vertex of edge to remove.
	 * @param tarLabel Target vertex of edge to remove.
	 */	
	public abstract void removeEdge(String srcLabel, String tarLabel);
	
	/**
	 * Prints the list of vertices to PrintWriter 'os'.
	 *
	 * @param os PrinterWriter to print to.
	 */
	public abstract void printVertices(PrintWriter os);


	/**
	 * Prints the list of edges to PrintWriter 'os'.
	 *
	 * @param os PrinterWriter to print to.
	 */
	public abstract void printEdges(PrintWriter os);
}
