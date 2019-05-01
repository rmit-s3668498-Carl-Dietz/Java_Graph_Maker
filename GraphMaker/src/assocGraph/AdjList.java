package assocGraph;
import java.io.*;
import java.util.*;

/**
 * Adjacency list implementation for the AssociationGraph interface.
 *
 *
 * @author Carl Dietz, 2019.
 */
public class AdjList extends AbstractAssocGraph
{
	//Map contains verticies and a pointer to their edge list
	private Map <String, Node> verList;
	
	//Reduce need to iterate through linked lists for weights by keeping separate map of edges
	private Map <String, List<String>> edgeIndex;
	
	//Contructs empty graph after initialisation.
    public AdjList() 
    {
    	 verList = new HashMap<String, Node>();
    	 edgeIndex = new  HashMap <String, List<String>>();

    } // end of AdjList()

    
    public void addVertex(String vertLabel) 
    {
    	if(!verList.containsKey(vertLabel))
    	{
    		verList.put(vertLabel, new Node(null));
 		   	edgeIndex.put(vertLabel, new ArrayList<String>());
 		   System.out.println("Vertex added");
    	}
    } // end of addVertex()

    public void addEdge(String srcLabel, String tarLabel, int weight) 
    {
       if(verList.containsKey(srcLabel) && verList.containsKey(tarLabel))
       {
    	   if(edgeIndex.get(srcLabel) == null || !edgeIndex.get(srcLabel).contains(tarLabel))
    	   {
    		   edgeIndex.get(srcLabel).add(tarLabel);
    		   
    		   Node currentNode = verList.get(srcLabel);
    		   while(currentNode.hasNextNode())
    		   {
    			   currentNode = currentNode.getNxtNode();
    		   }
    		   currentNode.setNxtNode(new Node(new MyPair(tarLabel, weight)));
    		   System.out.println("Edge added");
    	   }
       }
       else
       {
    	   System.err.println("Invalid vertex input.");
       }
    } // end of addEdge()

    public int getEdgeWeight(String srcLabel, String tarLabel) 
    {
    	 if(verList.containsKey(srcLabel) && verList.containsKey(tarLabel) && edgeIndex.containsKey(srcLabel))
         {
    		 if(edgeIndex.get(srcLabel).contains(tarLabel))
    		 {
	    		   Node currentNode = verList.get(srcLabel);
	      		   while(currentNode.hasNextNode())
	      		   {
		      			 if(currentNode.getNxtNode().getPair().getKey().equals(tarLabel))
						 {
							 return currentNode.getNxtNode().getPair().getValue();
						 }
		      			currentNode = currentNode.getNxtNode();
	      		   } 
    		 }
         }
		 return EDGE_NOT_EXIST;
    } // end of existEdge()

    public void updateWeightEdge(String srcLabel, String tarLabel, int weight) 
    {
    	if(verList.containsKey(srcLabel) && verList.containsKey(tarLabel) && edgeIndex.containsKey(srcLabel))
        {
	   		 if(edgeIndex.get(srcLabel).contains(tarLabel))
	   		 {
	   			   Node currentNode = verList.get(srcLabel);
	     		   while(currentNode.hasNextNode())
	     		   {
	     			 if(currentNode.getNxtNode().getPair().getKey().equals(tarLabel))
					 {
	     				 if(weight == 0)
	     				 {
	     					 removeEdge(srcLabel, tarLabel);
	     					 return;
	     				 }
	     				 else
	     				 {
							 currentNode.getNxtNode().setPair(new MyPair(tarLabel, weight));
							 return;
	     				 }
					 }
	     			currentNode = currentNode.getNxtNode();
	     		   } 
	   		 }
	   		else
	        {
	     	   System.err.println("Edge does not exist.");
	        }
        }
    	else
        {
     	   System.err.println("Invalid vertex input.");
        }
    } // end of updateWeightEdge()


    public void removeVertex(String vertLabel) 
    {
        if(verList.containsKey(vertLabel))
        {
        	if(edgeIndex.containsKey(vertLabel))
        	{
        		edgeIndex.remove(vertLabel);
        	}
        	  verList.remove(vertLabel);
        	  for(String s: edgeIndex.keySet())
              {
             	 if(edgeIndex.get(s).contains(vertLabel))
             	 {
             		 edgeIndex.get(s).remove(vertLabel);
             		 removeEdge(s, vertLabel);
             	 }
              }
        	 
        }
        else
        {
     	   System.err.println("Invalid vertex input.");
     	   return;
        }

    } // end of removeVertex()
    
    //method used to remove edges from linked lists
    public void removeEdge(String srcLabel, String tarLabel)
    {
    	Node currentNode;
    	
    	currentNode = verList.get(srcLabel);

        //indicates head node-keep searching
        if(currentNode.getPair() == null && !currentNode.hasNextNode())
        {
     	   return;
        }
        
        while(currentNode != null && currentNode.hasNextNode())
		   {
			 	if(currentNode.getNxtNode().getPair().getKey().equals(tarLabel))
				 {
	  			 		currentNode.setNxtNode(currentNode.getNxtNode().getNxtNode());
				 }
			 	
			 	currentNode = currentNode.getNxtNode();
		   }
        
    }


    public List<MyPair> inNearestNeighbours(int k, String vertLabel) 
    {
        List<MyPair> neighbours = new ArrayList<MyPair>();

        if(verList.containsKey(vertLabel))
        {
        	 for(String s: verList.keySet())
             {
        		Node currentNode = verList.get(s);
                while(currentNode.hasNextNode())
        		{
             		if(currentNode.getNxtNode().getPair().getKey().equals(vertLabel))
             		{
                		neighbours.add(new MyPair(s, currentNode.getNxtNode().getPair().getValue()));
             		}
             		currentNode = currentNode.getNxtNode();
             	}
             }
        	 
        }
        else
        {
     	   System.err.println("Invalid vertex input.");
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

        if(verList.containsKey(vertLabel))
        {
        	Node currentNode = verList.get(vertLabel);
        	
        	while(currentNode.hasNextNode())
     		{
          		if(k == -1 || currentNode.getNxtNode().getPair().getValue() >= k)
             	{
             		neighbours.add(currentNode.getNxtNode().getPair());
             	}
          		currentNode = currentNode.getNxtNode();
          	}
        	
        }
        else
        {
     	   System.err.println("Invalid vertex input.");
        }

        if(k == -1 || neighbours.size() <= k)
        {
        	return neighbours;
        }
        return sieveNeighboursByK(neighbours, k);
    } // end of outNearestNeighbours()


    public void printVertices(PrintWriter os) 
    {
    	for(String vertex: verList.keySet())
    	{
    		os.printf("%s ", vertex);
    	}
    } // end of printVertices()


    public void printEdges(PrintWriter os) 
    {
    	 for(String s: verList.keySet())
         {
         	Node currentNode = verList.get(s);
            while(currentNode.hasNextNode())
    		{
         		os.printf("%s %s %d\n", s, currentNode.getNxtNode().getPair().getKey(), currentNode.getNxtNode().getPair().getValue());	
         		currentNode = currentNode.getNxtNode();
         	}
         }
    } // end of printEdges()

    //Helper class to facilitate linked list implementation 
    protected class Node 
    {
    	private Node nxtNode;
    	private MyPair pair;
    	
    	public Node(MyPair pair) 
    	{
    		super();
    		this.pair = pair;
    	}
    	
		public Node getNxtNode() 
		{
			return nxtNode;
		}

		public void setNxtNode(Node nxtNode) 
		{
			this.nxtNode = nxtNode;
		}
		
		public boolean hasNextNode()
		{
			if(nxtNode == null)
			{
				return false;
			}
			
			return true;
		}

		public MyPair getPair() 
		{
			return pair;
		}

		public void setPair(MyPair pair) 
		{
			this.pair = pair;
		}
    }

} // end of class AdjList
