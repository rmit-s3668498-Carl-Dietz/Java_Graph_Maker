package assocGraph;
/**
 * Class implement a pair object.
 * Note javafx is not available on RMIT core teaching servers hence we developed our own.
 *
 * @author Jeffrey Chan, 2019
 */
public class MyPair 
{
	private String mVert;
	private Integer mWeight;

	public MyPair(String vert, Integer weight) 
	{
		mVert = vert;
		mWeight = weight;
	}


	public String getKey() 
	{
		return mVert;
	}

	public Integer getValue()
	{
		return mWeight;
	}
	
	public void setValue(Integer weight)
	{
		mWeight = weight;
	}
	
	/*
	 * //Dummied methods originally intended for use in adjacency list via contains() calls in Collections
	public boolean equals(Mypair p)
	{
		if(this.getKey().equals(p.getKey))
		{
			return true;
		}
		
		return false;
	}
	
	public int hashCode()
	{
		return this.getKey().hashCode();
	}*/
	
} // end of class MyPair
