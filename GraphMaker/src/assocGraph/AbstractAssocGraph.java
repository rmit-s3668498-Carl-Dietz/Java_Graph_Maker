package assocGraph;
import java.util.*;

/**
 * Abstract class for Association graph that implements common functionality.
 *
 * @author Carl Dietz, 2019.
 * 
 */

public abstract class AbstractAssocGraph implements AssociationGraph
{
	protected static final int EDGE_NOT_EXIST = -1;
	
	//Method used to extract the closest 'k' number of neighbours from a list
	 protected List<MyPair> sieveNeighboursByK(List<MyPair> neighbours, int k)
	 {
	    List<MyPair> tempNeighbours = neighbours;
	    List<MyPair> newNeighbours = new ArrayList<MyPair>();
	    	
	    for(int a = 0; a <k; a++)
	   	{
	    	int check = 0;
		    for(int i = 0; i<tempNeighbours.size(); i++)
		   	{
		    	if(tempNeighbours.get(i).getValue() > tempNeighbours.get(check).getValue())
		    	{
		    		check = i;
		    	}
		    }
	    	newNeighbours.add(tempNeighbours.get(check));
	    	tempNeighbours.remove(tempNeighbours.get(check));
	    }
	    	
	    return newNeighbours;
	}
	
	public void addEdge(String srcLabel, String tarLabel)
	{
		this.addEdge(srcLabel, tarLabel, 1);
	}

} // end of abstract graph AbstractAssocGraph
