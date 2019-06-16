package HowManyPackAreNeeded;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
 * this class is used to represent the Status of Player's Collection
 * the members are following :
 * Variable Name 	|			Meaning
 * 		dust_Need	| Value represent the total number of dust that required to create entire collection 
 *  	Golden		| representing the set of golden card that player had 
 *  	Epic   		| representing the set of epic card that player had
 *  	rare    	| representing the set of rare card that player had
 *  	Normal		| representing the set of normal card that player had
 *  	NumberOfPack|Value represent the total number of Pack that player have already Opened 
 */
public class HandCardStatus {
	int dust_Need;
	CardSet Golden;
	CardSet Epic;
	CardSet rare;
	CardSet Normal;
	int NumberOfPack;
	/**
	 * the initial function of CardPool
	 * @param TotalGolden
	 * 	the total number of golden card 
	 * @param TotalEpic
	 * 	the total number of epic card 
	 * @param TotalRare
	 * 	the total number of Rare card 
	 * @param TotalNormal
	 * 	the total number of Normal card 
	 */
	HandCardStatus(int TotalGolden,int TotalEpic, int TotalRare, int TotalNormal)
	{
		Golden = new CardSet(TotalGolden);
		Epic   = new CardSet(TotalEpic);
		rare   = new CardSet(TotalRare);
		Normal = new CardSet(TotalNormal);
		dust_Need = TotalGolden*1600+TotalEpic*400+TotalRare*100+TotalNormal*40;
		NumberOfPack = 0;
	}
	void OpenPackage(PackagePool P)
	{
		if( P.IsEmpty() )
		{
			dust_Need-= P.UnpickedDust;
			P.UnpickedDust = 0;
		}
		
		List<Card> Package = P.GetPackage();
		for( int i = 0 ; i < Package.size() ; i++)
		{
			Card C = Package.get(i);
			switch(C.Type)
			{
			case Golden:
				if(Golden.HaveGet.contains(C.index))
				{
					System.err.println("Error Pick up Repeated Golden Card");
				}else{
					this.dust_Need -= 1600;
					Golden.HaveGet.add(C.index);
					Golden.NotGet.remove(C.index);
				}
				break;
			case Epic:
				if( Epic.HaveGet.contains(C.index))
				{
					this.dust_Need -= 100;
					//get 100 dust by disenchant
				}else{
					this.dust_Need -= 400;
					//no need to create such card
					Epic.HaveGet.add(C.index);
					Epic.NotGet.remove(C.index);
				}
				break;
			case rare:
				if( rare.HaveGet.contains(C.index))
				{
					this.dust_Need -= 20;
					//get 20 dust by disenchant
				}else{
					this.dust_Need -= 100;
					//no need to create such card
					rare.HaveGet.add(C.index);rare.NotGet.remove(C.index);
				}
				break;
			case Common:
				if( Normal.HaveGet.contains(C.index))
				{
					this.dust_Need-=5;
					//get 5 dust by disenchant
				}else{
					this.dust_Need-=40;
					//no need to create such card
					Normal.HaveGet.add(C.index);Normal.NotGet.remove(C.index);
				}
				break;
			}
		}
		this.NumberOfPack++;
	}
	boolean IsOver()
	{
		if( this.dust_Need <= 0)
		{
			return true;
		}else
		{
			return false;
		}
	}
}
