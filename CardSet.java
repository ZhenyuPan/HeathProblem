package HowManyPackAreNeeded;

import java.util.HashSet;
import java.util.Set;

// the Card represent 
public class CardSet {
	Set<Integer> HaveGet;
	// Cards that have already been drawn   
	Set<Integer> NotGet;
	// Cards that belong to given kind but not been obtained yet 
	CardSet(int TotalNumberofCard)
	{
		HaveGet = new HashSet<Integer>();
		NotGet  = new HashSet<Integer>();
		for( int i  = 0 ; i < TotalNumberofCard ; i++ )
		{// i is the card Index 
			NotGet.add(i);
			// all the card will be initialized as "not get yet"
		}
	}
}
