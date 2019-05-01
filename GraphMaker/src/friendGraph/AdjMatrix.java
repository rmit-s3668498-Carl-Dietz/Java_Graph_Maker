package friendGraph;
import java.io.*;
import java.util.*;
import java.util.Map.Entry;

/**
 * Adjacency matrix implementation for the FriendshipGraph interface.
 *
 * @author Joshua Ooi, Carl Dietz 2016.
 */
public class AdjMatrix implements FriendshipGraph
{
	//2D Array stores Adjacency Matrix values: Axes represent edges; values are edge between them
	private int[][] aMatrix;
	
	//Hashmaps store Vertices and Edges and associate with indexes for the aMatrix
	private Map <String, Integer> verIndecies;
	
	
	/**
	 * Constructs empty graph.
	 */
    public AdjMatrix() 
    {
    	aMatrix = new int[0][0];
    	verIndecies = new HashMap<String, Integer>();
    	
    } // end of AdjMatrix()
    
    
    public void addVertex(String vertLabel) 
    {
        verIndecies.put(vertLabel, verIndecies.size());
         
	    expandArray();
	    System.out.println("Vertex added");
     
    } // end of addVertex()
	
    
    public void addEdge(String srcLabel, String tarLabel) 
    {
    	int srcIndex = verIndecies.get(srcLabel);
    	int tarIndex = verIndecies.get(tarLabel);
    	
    	this.aMatrix[srcIndex][tarIndex]= 1;
    	this.aMatrix[tarIndex][srcIndex]= 1;
    	System.out.println("Edge added");
    	
    } // end of addEdge()
	

    public ArrayList<String> neighbours(String vertLabel) 
    {
        ArrayList<String> neighbours = new ArrayList<String>();
        
        int index = this.verIndecies.get(vertLabel);
        
        for(int i = 0; i < this.aMatrix.length; i++)
        {
        	if(this.aMatrix[index][i] == 1)
        	{
        		neighbours.add((i +""));
        	}
        }
        
        return neighbours;
    } // end of neighbours()
    
    
    public void removeVertex(String vertLabel) 
    {
    	int removevalue = this.verIndecies.get(vertLabel);
    	
    	this.verIndecies.remove(vertLabel);
    	
    	for(Map.Entry<String, Integer> entry : this.verIndecies.entrySet()) 
    	{
    	
    		String key = entry.getKey();
    	    int value = entry.getValue();
    	    
    	    if(value > removevalue)
    	    {
    	    	this.verIndecies.replace(key, value-1);
    	    }
    	}
    	
    	removeVertexFromMatrix(removevalue);
    } // end of removeVertex()
	
    
    public void removeEdge(String srcLabel, String tarLabel) 
    {
    	int srcIndex = verIndecies.get(srcLabel);
    	int tarIndex = verIndecies.get(tarLabel);
    	
    	aMatrix[srcIndex][tarIndex] = 0;
    	aMatrix[tarIndex][srcIndex] = 0;

    } // end of removeEdges()
	
    
    public void printVertices(PrintWriter os) 
    {
        // Implement me!
    		String output = "";
        for(String vertex : this.verIndecies.keySet())
        {
        		output +=vertex.toString()+" "; 
        }
        
        os.println(output);
    } // end of printVertices()
	
    
    public void printEdges(PrintWriter os) 
    {
    	String holder = "";
    		
    	for(int i = 0; i < this.aMatrix.length; i++) 
    	{
    			
    		for(int j = 0; j < this.aMatrix.length; j++)
    		{
    				
    			if(this.aMatrix[i][j] == 1)
    			{
    				holder += getVertexByValue(i).toString() + " " + getVertexByValue(j).toString() + "\n";
    			}
    		}
    	}
    	os.println(holder);
    } // end of printEdges()
    
    //Method written by Joshua Ooi
    public int shortestPathDistance(String vertLabel1, String vertLabel2) 
    {
    	int vert1Index;
    	int vert2Index = this.verIndecies.get(vertLabel2);
    	LinkedList<String> queue = new LinkedList<String>();
    	boolean[] visited = new boolean[this.aMatrix.length];
    	int count = 0;
    	queue.add(vertLabel1);
    	String vertex;
    	
    	while(!queue.isEmpty())
    	{
    		vertex = queue.poll();
    		vert1Index = this.verIndecies.get(vertex);
    		visited[vert1Index] = true;
    		count++;
    		
    		if(this.aMatrix[vert1Index][vert2Index] == 1) {
    			return count;
    		}
    		
    		for(String vert : neighbours(vertex))
    		{
    			if(visited[this.verIndecies.get(vert)] == true)
    			{
    				continue;
    			}
    			if(queue.contains(vert) == false)
    			{
    				queue.add(vert);
    			}
    		}
    	}
        // if we reach this point, source and target are disconnected
        return disconnectedDist;    	
    } // end of shortestPathDistance()
    
    private void expandArray() 
    {
	    	int max = verIndecies.size();
	    	int newArray[][] = new int[max][max];
	
	    	for(int i = 0; i < this.aMatrix.length; i++)
	    	{
	    		for(int j = 0; j <= i; j++)
	    		{
	    			newArray[i][j] = this.aMatrix[i][j];
	    			newArray[j][i] = this.aMatrix[j][i];
	    		}
	    	}
	    	for(int k = 0; k < max;k++)
	    	{
	    		newArray[max-1][k] = 0;
	    		newArray[k][max-1] = 0;
	    	}
	    	
	    	this.aMatrix = newArray;
	    	
	    	newArray = null;
    }
    
    //Creates a new array without a specified vale
    private void removeVertexFromMatrix(int value)
    {
    	int size = this.verIndecies.size();
    	int[][] newArray = new int[size][size];
    	int m = 0;
    	int n = 0;
    	
    	for(int i = 0; i < this.aMatrix.length; i++)
    	{
    		n = 0;
    		if(i != value && m <= size)
    		{
    			for(int j = 0; j < this.aMatrix.length; j++)
        		{
        			if(j != value && n <= size)
        			{
        				newArray[m][n] = this.aMatrix[i][j];
        				n++;
        			}
        		}
    			
    			m++;
    		}
    	}
    	
    	this.aMatrix = newArray;
    	
    	newArray = null;
    }
    
    private String getVertexByValue(int value)
    {
    	for(Entry<String, Integer> entry : this.verIndecies.entrySet()) 
    	{
    		String key = entry.getKey();
   		    int currentvalue = entry.getValue();
    		if(currentvalue == value)
    		{
    		    return key;
    		}
    	}
    		return null;
    }
} // end of class AdjMatrix
