package assocGraph;
import java.io.*;
import java.util.*;

/**
 * Incident matrix implementation for the AssociationGraph interface.
 *
 * @author Carl Dietz, 2019.
 */

public class IncidenceMatrix extends AbstractAssocGraph
{
	private int[][] indcMatrix;
	private Map <String, Integer> vrtxList;
	private Map <Edge, Integer> edgeList;
	
	/**
	 * Contructs empty graph upon initialisation.
	 */
    public IncidenceMatrix() 
    {
    	//2D Array stores Incidence Matrix values
    	indcMatrix = new int[0][0];
    	
    	//Hashmaps store Vertices and Edges, associating them with indexes for the array to use
    	vrtxList = new LinkedHashMap<String, Integer>();
    	edgeList = new LinkedHashMap<Edge, Integer>();
    } // end of IncidentMatrix()


    public void addVertex(String vertLabel) 
    {
    	if(!vrtxList.containsKey(vertLabel))
    	{
    		vrtxList.put(vertLabel, (indcMatrix.length));
	        expandArray();
	        System.out.println("Vertex added");
	        
    	}
    } // end of addVertex()

    public void addEdge(String srcLabel, String tarLabel, int weight) 
    {
    	if(edgeExists(srcLabel, tarLabel))
    	{
    		return;
    	}
    	if(vrtxList.containsKey(srcLabel) && vrtxList.containsKey(tarLabel))
    	{
    		//Sublate vertex pairs into edge.
    		Edge newEdge = new Edge(srcLabel, tarLabel);
	    	
	    	edgeList.put(newEdge, (indcMatrix[0].length));
	    	
	    	expandArray();

	    	indcMatrix[vrtxList.get(srcLabel)][edgeList.get(newEdge)]=weight;
	    	if(!tarLabel.equals(srcLabel))
	    	{
	    		indcMatrix[vrtxList.get(tarLabel)][edgeList.get(newEdge)]=-weight;
	    	}
	    	System.out.println("Edge added");
    	}
    	else
        {
     	   System.err.println("Invalid vertex input.");
        }
    	
    } // end of addEdge()


	public int getEdgeWeight(String srcLabel, String tarLabel) 
	{
		if(vrtxList.containsKey(srcLabel) && vrtxList.containsKey(tarLabel))
		{
			for(Edge e: edgeList.keySet())
			{
				if(e.getSrcVertex().equals(srcLabel) && e.getTarVertex().equals(tarLabel))
				{
					return (indcMatrix[vrtxList.get(srcLabel)][edgeList.get(e)]);
				}
			}
		}
		else
	    {
	    	System.err.println("Invalid vertex input.");
	    }
		return EDGE_NOT_EXIST;
	} // end of existEdge()


	public void updateWeightEdge(String srcLabel, String tarLabel, int weight) 
	{
		if(vrtxList.containsKey(srcLabel) && vrtxList.containsKey(tarLabel))
    	{
			if(weight == 0)
			{
				removeEdge(srcLabel, tarLabel);
				return;
			}
			
			for(Edge e: edgeList.keySet())
			{
				if(e.getSrcVertex().equals(srcLabel) && e.getTarVertex().equals(tarLabel))
				{
						(indcMatrix[vrtxList.get(srcLabel)][edgeList.get(e)]) = weight;
						if(!e.getSrcVertex().equals(e.getTarVertex()))
						{
							(indcMatrix[vrtxList.get(tarLabel)][edgeList.get(e)]) = -weight;
						}
					
				}
			}
    	}
		else
	    {
	    	System.err.println("Invalid vertex input.");
	    }
    } // end of updateWeightEdge()

    public void removeVertex(String vertLabel) 
    {
    	if(vrtxList.containsKey(vertLabel))
    	{
    		//Check for self edges
    		if(edgeExists(vertLabel, vertLabel))
    		{
    			removeEdge(vertLabel, vertLabel); 
    		}
	    	for(String s:vrtxList.keySet())
	    	{
	    		//Vertex must be removed from edges as both source and target
	    		if(edgeExists(vertLabel, s))
	    		{
	    			removeEdge(vertLabel, s); 
	    		}
	    		if(edgeExists(s, vertLabel))
	    		{
	    			removeEdge(s, vertLabel);
	    		}
	    	}
	    	removeRow(vrtxList.get(vertLabel));
	    	vrtxList.remove(vertLabel);
	    	
	    	//The indcMatrix must be updated once the vertex and its edges are removed
	    	updateVerticies();	
	    	
	    	
    	}
    	else
	    {
	    	System.err.println("Invalid vertex input.");
	    }
    } // end of removeVertex()
    
    public void removeEdge(String srcLabel, String tarLabel) 
    {
    	printEdges(new PrintWriter(System.out));
    	if(vrtxList.containsKey(srcLabel) && vrtxList.containsKey(tarLabel) && edgeExists(srcLabel, tarLabel))
    	{
	    	Iterator<Edge> iter = edgeList.keySet().iterator();
	    	
	    	while(iter.hasNext())
	    	{
	    		Edge e = iter.next();
	    		if(e.getSrcVertex().equals(srcLabel) && e.getTarVertex().equals(tarLabel))
	    		{
	    			removeColumn(edgeList.get(e));
	    			iter.remove();
	    			updateEdges();
	    			continue;
	    		}
	    	}
    	}
    }
    
    //Rebuilds edgeList for indcMatrix index integrity
    public void updateEdges()
    {
    	int a = 0;
		for(Edge e: edgeList.keySet())
		{
			edgeList.put(e, a);
			a++;
		}
    }
    
    //Rebuilds vertList for indcMatrix index integrity
    public void updateVerticies()
    {
    	int a = 0;
		for(String s: vrtxList.keySet())
		{
			vrtxList.put(s, a);
			a++;
		}
    }

	public List<MyPair> inNearestNeighbours(int k, String vertLabel) 
	{
		 List<MyPair> neighbours = new ArrayList<MyPair>();
		 
		 if(vrtxList.containsKey(vertLabel) && k>= -1)
		 {
	        for(Edge e: edgeList.keySet())
	        {
	        	if(e.getTarVertex().equals(vertLabel))
	        	{
	        		neighbours.add(new MyPair(e.getSrcVertex(), indcMatrix[vrtxList.get(e.getSrcVertex())][edgeList.get(e)]));
	        	}
	        }
		 } 
		 if(k == -1 || neighbours.size() <= k)
	     {
	          return neighbours;
	     }
	     return sieveNeighboursByK(neighbours, k);
    } // end of inNearestNeighbours()

    public List<MyPair> outNearestNeighbours(int k, String vertLabel) 
    {
    	List<MyPair> neighbours = new ArrayList<MyPair>();
		 
		 if(vrtxList.containsKey(vertLabel) && k>= -1)
		 {
	        for(Edge e: edgeList.keySet())
	        {
	        	if(e.getSrcVertex().equals(vertLabel))
	        	{
	        			neighbours.add(new MyPair(e.getTarVertex(), indcMatrix[vrtxList.get(vertLabel)][edgeList.get(e)]));
	        	}
	        }
		 } 
		 if(k == -1 || neighbours.size() <= k)
	     {
			 return neighbours;
	     }
		 
	     return sieveNeighboursByK(neighbours, k);
    } // end of outNearestNeighbours()

    public void printVertices(PrintWriter os) 
    {
    	for(String vertex: vrtxList.keySet())
    	{
    		os.printf("%s ", vertex);
    	}
    } // end of printVertices()

    public void printEdges(PrintWriter os)
    {
    	for(Edge e: edgeList.keySet())
    	{
    		os.printf("%s %d\n", e.toString(), indcMatrix[vrtxList.get(e.getSrcVertex())][edgeList.get(e)]);
    	}
    	
    } // end of printEdges()
    
  //Expand indcMatrix after adding edge or vertex
    private void expandArray() 
    {
    	int newArray[][] = new int[vrtxList.size()][edgeList.size()];
    	
    	for(int i = 0; i < indcMatrix.length; i++)
    	{
    		for(int j = 0; j < indcMatrix[0].length; j++)
    		{
    			newArray[i][j] = indcMatrix[i][j];
    		}
    	}
    	
    	indcMatrix = newArray;
    	newArray = null;
    }
    
    //shrink indcMatrix after removing edge or vertex   
    private void removeColumn(int colIndex)
    {
    	
    	int newArray[][] = new int[vrtxList.size()][edgeList.size()-1];
    	int a = 0, b = 0;
    	for(int i = 0; i < indcMatrix.length; i++)
    	{
    		b = 0;
    		for(int j = 0; j < indcMatrix[i].length; j++)
    		{
    			if(j == colIndex)
    			{
    				continue;
    			}
    			newArray[a][b] = indcMatrix[i][j];
    			b++;
    		}
    		a++;
    	}
    	
    	indcMatrix = newArray;
    }
    
    private void removeRow(int rowIndex)
    {
    	
    	int newArray[][] = new int[vrtxList.size()-1][edgeList.size()];
    	int a = 0, b = 0;
    	for(int i = 0; i <  indcMatrix.length; i++)
    	{
    		if(i == rowIndex)
			{
				continue;
			}
    		b = 0;
    		for(int j = 0; j <  indcMatrix[i].length; j++)
    		{
    			newArray[a][b] = indcMatrix[i][j];
    			b++;
    		}
    		a++;
    	}
    	indcMatrix = newArray;
    }
    
    //Check if edge exists in indcMatrix
    private boolean edgeExists(String srcLabel, String tarLabel)
    {	
    	Edge e = new Edge(srcLabel, tarLabel);
    	for(Edge i: edgeList.keySet())
    	{
    		if(i.getSrcVertex().equals(e.getSrcVertex()) && i.getTarVertex().equals(e.getTarVertex()))
    		{
    			return true;
    		}
    	}
    	return false;
    }
} // end of class IncidenceMatrix

//Helper class to hold Edges as pairs of strings()
class Edge  
{
	private String tarVertex;
	private String srcVertex;
	
	public Edge(String src, String tar)
	{
		this.srcVertex = src;
		this.tarVertex = tar;
	}

	public String getTarVertex() 
	{
		return tarVertex;
	}

	public String getSrcVertex() 
	{
		return srcVertex;
	}
	
	@Override
	public String toString()
	{
		return(new String(srcVertex + " " + tarVertex));
	}
}
