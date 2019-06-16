package HowManyPackAreNeeded;
/**
 * In order to use the HashSet , we need to Override two function 
 * 	1) hashCode
 *  2) equals
 * @author zheny
 *
 */
public class Card {
	Integer 	index;
	CardType Type;
	@Override
	public boolean equals(Object o)
	{
		if( o instanceof Card)
		{
			Card C= (Card)o;
			if( index == C.index && Type == C.Type)
			{
				return true;
			}
		}
		return false;
	}
	@Override 
	public int hashCode()
	{
		int TypeCode = 0;
		switch(Type)
		{
		case Golden:TypeCode = 0;
		case Epic:TypeCode = 1;
		case rare:TypeCode = 2;
		case Common:TypeCode = 3;
		}
		return index.hashCode()*4+TypeCode;
	}
	
	public String Display()
	{
		return "<"+index+","+Type+">";
	}
}
