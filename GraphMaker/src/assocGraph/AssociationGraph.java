package assocGraph;

import java.util.List;

import graph.MyGraph;

/**
 * Association graph interface used to implement the communication graphs.
 *
 * Used as guideline to construct remainder of assignment by Carl Dietz
 *
 * @author Jeffrey Chan, 2019.
 */
public interface AssociationGraph extends MyGraph
{
	/**
	 * Adds an edge to the graph.  If the edge already exists in the graph, no changes are made.  If one of the vertices doesn't exist, a warning to System.err should be issued
	 * and no edge or vertices should be added.
	 *
	 * @param srcLabel Source vertex of edge to add.
	 * @param tarLabel Target vertex of edge to add.
	 * @param weight Integer weight to add between edges.
	 */
	public abstract void addEdge(String srcLabel, String tarLabel, int weight);


	/**
	 * Get the edge weight.
	 *
	 * @param srcLabel Source vertex of edge to check.
	 * @param tarLabel Target vertex of edge to check.
	 *
	 * @returns int Value of edge weight.  If edge doesn't exist, return -1;
	 */
	public abstract int getEdgeWeight(String srcLabel, String tarLabel);


	/**
	 * Update the weight of an edge in the graph.  If the edge doesn't exists in the graph, no changes are made, but a warning to System.err should be issued.  If one of the
	 * vertices doesn't exist, a warning to System.err should be issued and no edge or vertices should be added.
	 *
	 * @param srcLabel Source vertex of edge to update weight of.
	 * @param tarLabel Target vertex of edge to update weight of.
	 * @param weight Weight to update edge to.  If weight = 0, delete the edge.
	 */
	public abstract void updateWeightEdge(String srcLabel, String tarLabel, int weight);


	/**
     * Returns the set of in-neighbours for a vertex.  If vertex doesn't exist, then a warning to System.err should be issued.
     *
     * @param vertLabel Vertex to find the in-neighbourhood for.
     *
     * @returns The set of in-neighbours of vertex 'vertLabel' and their associated edge weights.
     */
    public abstract List<MyPair> inNearestNeighbours(int k, String vertLabel);


	/**
     * Returns the set of out-neighbours for a vertex.  If vertex doesn't exist, then a warning to System.err should be issued.
     *
     * @param vertLabel Vertex to find the out-neighbourhood for.
     *
     * @returns The set of out-neighbours of vertex 'vertLabel' and their associated edge weights.
     */
    public abstract List<MyPair> outNearestNeighbours(int k, String vertLabel);

} // end of interface AssociationGraph
