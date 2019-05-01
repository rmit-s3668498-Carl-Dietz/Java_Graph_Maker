package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import assocGraph.AdjList;
import assocGraph.AssociationGraph;
import assocGraph.IncidenceMatrix;
import assocGraph.MyPair;
import friendGraph.AdjMatrix;
import friendGraph.FriendshipGraph;
import friendGraph.IndMatrix;
import graph.MyGraph;

/**
 * Framework to test the graph implementations.
 *
 *
 * @author Carl Dietz, 2019.
 */
public class GraphEval 
{
	/** Name of class, used in error messages. */
	protected static final String progName = "GraphEval";

	/** Standard outstream. */
	protected static final PrintStream outStream = System.out;
	
	public static void main(String args[])
	{
		MyGraph graph = null;
		boolean isAssocGraph = false;
		
		if(args.length == 0)
		{
			System.err.println("Invalid Arguments! ");
			usage(progName);
		}
		switch(args[0])
		{
			case "adjList":
			{
				graph = new AdjList();
				isAssocGraph = true;
				break;
			}
			case "asocIndMat":
			{
				graph = new IncidenceMatrix();
				isAssocGraph = true;
				break;
			}
			case "frndIndMat":
			{
				graph = new IndMatrix();
				isAssocGraph = false;
				break;
			}
			case "adjMat":
			{
				graph = new AdjMatrix();
				isAssocGraph = false;
				break;
			}
			default:
			{
				System.err.println("Invalid Arguments! ");
				usage(progName);
				System.exit(0);
			}
		}
		
		processInput(graph, isAssocGraph);
	}
	
	private static void processInput(MyGraph graph, boolean isAssocGraph)
	{
		BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));
		String line;
		int lineNum = 1;

		// continue reading in commands until we either receive the quit signal or there are no more input commands
		try
		{
			while ((line = inReader.readLine()) != null)
			{
				if(!processOperations(line, lineNum, graph))
				{
					break;
				}
				
				if(isAssocGraph)
				{
					processAssocOperations(line, lineNum, (AssociationGraph) graph);
				}
				else
				{
					processFriendshipOperations(line, lineNum, (FriendshipGraph) graph);
				}
				lineNum++;
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private static boolean processOperations(String line, int lineNum, MyGraph graph)
	{
		PrintWriter outWriter = new PrintWriter(System.out, true);

		// continue reading in commands until we either receive the quit signal or there are no more input commands
		try 
		{

				String[] tokens = line.split(" ");

				// check if there is at least an operation command
				if (tokens.length < 1) 
				{
					System.err.println(lineNum + ": not enough tokens.");
					lineNum++;
					return true;
				}

				String command = tokens[0];
				// determine which operation to execute
				switch (command.toUpperCase()) 
				{
					// add vertex
					case "AV":
						if (tokens.length == 2) 
						{
							graph.addVertex(tokens[1]);
						}
						else 
						{
							System.err.println(lineNum + ": incorrect number of tokens.");
						}
						break;
							
			       //Remove vertex
					case "RV":
						if (tokens.length == 2) 
						{
							graph.removeVertex(tokens[1]);
						}
						else 
						{
							System.err.println(lineNum + ": incorrect number of tokens.");
						}
						break;
							
						//Remove edge	
					case "RE":
						if (tokens.length == 3) 
						{
							graph.removeEdge(tokens[1], tokens[2]);
						}
						else 
						{
							System.err.println(lineNum + ": incorrect number of tokens.");
						}
						break;
						// print vertices
					case "PV":
						graph.printVertices(outWriter);
						break;
		            // print edges
					case "PE":
						graph.printEdges(outWriter);
						break;
					// quit
					case "Q":
						return false;
					default:
				} // end of switch()
		} 
		catch(IllegalArgumentException ex)
		{
			ex.printStackTrace();
		}
		return true;
	}
	
	private static void processFriendshipOperations(String line, int lineNum, FriendshipGraph graph)
	{

		PrintWriter outWriter = new PrintWriter(System.out, true);

		// continue reading in commands until we either receive the quit signal or there are no more input commands
		try 
		{

				String[] tokens = line.split(" ");

				// check if there is at least an operation command
				if (tokens.length < 1) {
					System.err.println(lineNum + ": not enough tokens.");
					lineNum++;
					return;
				}

				String command = tokens[0];
				// determine which operation to execute
				switch (command.toUpperCase()) 
				{
				            // add edge
					case "AE":
						if (tokens.length == 3) 
						{
							graph.addEdge(tokens[1], tokens[2]);
						}
						else {
							System.err.println(lineNum + ": incorrect number of tokens.");
						}
					break;       
						
					// neighbourhood
					case "N":
						if (tokens.length == 2) 
						{
							ArrayList<String> neighbours = graph.neighbours(tokens[1]);
							StringBuffer buf = new StringBuffer();
							for (String neigh : neighbours) 
							{
								buf.append(" " + neigh);
							}
							outWriter.println(tokens[1] + buf.toString());
						}
						else 
						{
							System.err.println(lineNum + ": incorrect number of tokens.");
						}
	
					break;
						
					case "S":
						if (tokens.length == 3) 
						{
							outWriter.println(tokens[1] + " " + tokens[2] + " " + graph.shortestPathDistance(tokens[1], tokens[2]));
						}
						else 
						{
							System.err.println(lineNum + ": incorrect number of tokens.");
						}
					break;				
					default:
				} // end of switch()
				lineNum++;
			
		} 
		catch(IllegalArgumentException ex)
		{
			ex.printStackTrace();
		}
	}
	
	private static void processAssocOperations(String line, int lineNum, AssociationGraph graph)
	{
		PrintWriter outWriter = new PrintWriter(System.out, true);

		// continue reading in commands until we either receive the quit signal or there are no more input commands
		try 
		{

				String[] tokens = line.split(" ");

				// check if there is at least an operation command
				if (tokens.length < 1) {
					System.err.println(lineNum + ": not enough tokens.");
					lineNum++;
					return;
				}

				String command = tokens[0];
				// determine which operation to execute
				switch (command.toUpperCase()) 
				{
			        // add edge
					case "AE":
						if (tokens.length == 3) 
						{
							int weight = Integer.parseInt(tokens[3]);
							if (weight < 0) 
							{
								System.err.println(lineNum + ": edge weight must be non-negative.");
							}
							else 
							{
								graph.addEdge(tokens[1], tokens[2]);
							}
						}
						else 
						{
							System.err.println(lineNum + ": incorrect number of tokens.");
						}
					break;
					
					// get edge weight
					case "W":
						if (tokens.length == 3) 
						{
							int answer = graph.getEdgeWeight(tokens[1], tokens[2]);
							outWriter.println(answer);
						}
						else 
						{
							System.err.println(lineNum + ": incorrect number of tokens.");
						}
						break;
					// update weight of edge
					case "U":
						if (tokens.length == 4) 
						{
							int weight = Integer.parseInt(tokens[3]);
							if (weight < 0) 
							{
								System.err.println(lineNum + ": edge weight must be non-negative.");
							}
							else {
								graph.updateWeightEdge(tokens[1], tokens[2], weight);
							}
						}
						else 
						{
							System.err.println(lineNum + ": incorrect number of tokens.");
						}
					break;
					
					// neighbourhood
					case "ON":
						if (tokens.length == 3) 
						{
							List<MyPair> neighbours = graph.outNearestNeighbours(Integer.parseInt(tokens[1]), tokens[2]);
							StringBuffer buf = new StringBuffer();
							for (MyPair neigh : neighbours) 
							{
								buf.append(" (" + neigh.getKey() + "," + neigh.getValue() + ")");
							}

							outWriter.println(tokens[2] + buf.toString());
						}
						else {
							System.err.println(lineNum + ": incorrect number of tokens.");
						}

						break;
					// k-nearest in-neighbourhood
					case "IN":
						if (tokens.length == 3) 
						{
							List<MyPair> neighbours = graph.inNearestNeighbours(Integer.parseInt(tokens[1]), tokens[2]);
							StringBuffer buf = new StringBuffer();
							for (MyPair neigh : neighbours) 
							{
								buf.append(" (" + neigh.getKey() + "," + neigh.getValue() + ")");
							}

							outWriter.println(tokens[2] + buf.toString());
						}
						else {
							System.err.println(lineNum + ": incorrect number of tokens.");
						}

					break;
					default:
				} // end of switch()
				lineNum++;
			
		} 
		catch(IllegalArgumentException ex)
		{
			ex.printStackTrace();
		}
	}
	
	public static void usage(String progName) 
	{
		System.err.println(progName + ": <implementation>");
		System.err.println("<implementation> = <adjList | asocIndMat | frndIndMat | adjMat>");
		System.exit(1);
	} // end of usage
}

