package friendGraph;
import java.util.*;

import graph.MyGraph;


/**
 * Friendship graph interface. 
 *
 * Note, you should not need to modify this.
 *
 * @author Jeffrey Chan, 2016.
 */
public interface FriendshipGraph extends MyGraph
{
    /** Distance value for source and target vertex pairs that are disconnected. */
    public static final int disconnectedDist = -1;
        
        
    /**
     * Returns the set of neighbours for a vertex.  If vertex doesn't exist, then a warning to System.err should be issued.
     *
     * @param vertLabel Vertex to find the neighbourhood for.
     *
     * @returns The set of neighbours of vertex 'vertLabel'.
     */
    public abstract ArrayList<String> neighbours(String vertLabel);
	
	
	/**
	 * Computes the shortest path distance between two vertices.  If one or both of the vertices doesn't exist, then a warning to System.err should be issued.
	 *
	 * Note for undirected graph, which vertex is the origin and which is the destination doesn't matter, i.e., distance(A,B) = distance (B,A).
	 *
	 * @param vertLabel1 Origin vertex to compute shortest path distance from.  
	 * @param vertLabel2 Destination vertex to compute shortest path distance to.
	 *
	 * @returns Shortest path distance.  If there is no path between the vertices, then the value of FriendshipGraph.disconnectedDist should be returned.
	 */
	public abstract int shortestPathDistance(String vertLabel1, String vertLabel2);
	
} // end of interface FriendshipGraph