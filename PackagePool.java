package HowManyPackAreNeeded;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

// the card Package pool used to request package
// As in HearthStone, there are some rule for generate card.
// such as :
//			1. never generate duplication Golden
//   		2. System must generate at least one Golden for every 40 package, if the user have not collected all golden card yet.
//			 															   otherwise give user 1000 dust
//			3. each package must contain at least one card above normal
// Each time user request a package from the Package Pool 
// if the Package pool is empty we generate 40 package every time when the PackagePool is empty 
// we assume every card have the same chance to be generated 
public class PackagePool {
	List<Card> CardSet = new ArrayList<Card>();
	// one question: can we get a List from HashSet? well, we only get a iterator, that means we need to generate a list 
	List<List<Card>> Pool = new ArrayList<List<Card>>();
	// the Package list 
	int GoldenBound;
	// CardSet[0~GoldenBoud] are all golden card 
	int NormalBound;
	// CardSet[NormalBoud~] are all Normal Card 
	int UnpickedDust = 0;
	/**
	 * Pick a random Golden Card from Golden Set 
	 * @return the Golden Card system selected 
	 */
	private boolean Pick_A_GoldenCard(List<Card> Package)
	{
		if( this.GoldenBound == 0 )
		{
			return true;
		}
		Random rand  = new Random();
		int Index = rand.nextInt(this.GoldenBound);
		Card SelectedCard = new Card();
		SelectedCard.index = CardSet.get(Index).index;
		SelectedCard.Type  = CardSet.get(Index).Type;
		// now finish selected a random Golden card 
		CardSet.remove(Index);
		this.GoldenBound--;
		// remove such golden card;
		Package.remove(0);
		// remove one card from package pool
		Package.add(SelectedCard);
		// add such card into Package Pool
		return false;
	}
	/**
	 * Pick an available random Card from CardSet 
	 * @return true if such card is golden  
	 */
	private boolean Pick_A_randomCard(List<Card> Package)
	{
		boolean SelectedGolden = false;
		//1: Pick a random Card
		Random rand = new Random(); int CardIndex = rand.nextInt(CardSet.size()); 
		Card SelectedCard = new Card(); SelectedCard.index = CardSet.get(CardIndex).index ; SelectedCard.Type = CardSet.get(CardIndex).Type;
		//2: Check if such Card is Golden , if such Card is Golden , delete such card from both InitalSet and GoldenIndexSet
		if( SelectedCard.Type == CardType.Golden)
		{
			CardSet.remove(CardIndex);
			this.GoldenBound--;
			//remove Golden Card from Card Set
			SelectedGolden = true;
		}
		//3: add the selected Card into Package poor  
		Package.add(SelectedCard);
		return SelectedGolden;
	}
	/**
	 * Pick an available uncommon card from cardset 
	 * @param Package
	 * @return true if such card is golden
	 */
	private boolean Pick_A_UnCommonCard(List<Card> Package)
	{
		boolean SelectedGolden = false;
		//1: Pick a random Card
		Random rand = new Random(); int CardIndex = rand.nextInt(this.NormalBound); 
		Card SelectedCard = new Card(); SelectedCard.index = CardSet.get(CardIndex).index ; SelectedCard.Type = CardSet.get(CardIndex).Type;
		//2: Check if such Card is Golden , if such Card is Golden , delete such card from both InitalSet and GoldenIndexSet
		if( SelectedCard.Type == CardType.Golden)
		{
				CardSet.remove(CardIndex);
				this.GoldenBound--;
				//remove Golden Card from Card Set
				SelectedGolden = true;
		}
		//3: add the selected Card into Package poor  
		Package.add(SelectedCard);
		return SelectedGolden;
	}
	/**
	 * 
	 * @return true if the Package contain Golden
	 */
	private boolean Generate_A_Package(List<Card> Package)
	{
		boolean HasGolden = false;
		for( int i = 0 ; i < 5 ; i++)
		{
			if(Pick_A_randomCard(Package) )
			{
				HasGolden = true;
			}
		}
		boolean AllCommon = true;
		for( int i= 0 ; i < 5 ;i++)
		{
			Card SelectedCard = Package.get(i);
			if(SelectedCard.Type != CardType.Common)
			{
				AllCommon = false;
			}
		}
		if(AllCommon == true)
		{
			Package.remove(0);
			//remove the card at beginning 
			HasGolden = Pick_A_UnCommonCard(Package);
		}
		return HasGolden;
	}
	private void GenerateFortyPackage()
	{
		boolean HasGolden = false;
		for( int i = 0 ; i < 40 ; i++)
		{
			List<Card> Package = new ArrayList<Card>();
			//1: Generate a random Package 
			if( this.Generate_A_Package(Package))
			{
				HasGolden = true;
			}
			//2: add the Package into pool 
			this.Pool.add(Package);
		}
		// if the poor have no golden card right now, pick up a random golden card and put it into the card pool
		if( HasGolden == false)
		{
			List<Card> Package = this.Pool.get(this.Pool.size()-1);
			Package.remove(0);
			if( this.Pick_A_GoldenCard(Package) )
			{
				this.UnpickedDust = 1000;
			}
		}
		this.UnpickedDust = 0;
		// return 0 dust;
	}
	PackagePool(int TotalGolden,int TotalEpic, int TotalRare, int TotalNormal)
	{
		for( int i= 0 ; i < TotalGolden ; i++)
		{
			Card NewCard = new Card();
			NewCard.index = i ;
			NewCard.Type  = CardType.Golden;
			CardSet.add(NewCard);
			//add index into GoldenIndex List;
		}
		this.GoldenBound = TotalGolden;
		for( int i= 0 ; i < TotalEpic ; i++ )
		{
			Card NewCard = new Card();
			NewCard.index = i;
			NewCard.Type = CardType.Epic;
			CardSet.add(NewCard);
		}
		for( int i= 0 ; i <TotalRare ; i++ )
		{
			Card NewCard = new Card();
			NewCard.index = i;
			NewCard.Type = CardType.rare;
			CardSet.add(NewCard);
		}
		this.NormalBound = TotalGolden+TotalEpic+TotalRare;
		for( int i=0 ; i < TotalNormal ; i++)
		{
			Card NewCard = new Card();
			NewCard.index = i;
			NewCard.Type = CardType.Common;
			CardSet.add(NewCard);
		}
	}
	
	List<Card> GetPackage()
	{
		if(Pool.isEmpty())
		{// if the pool is empty, we generate another 40 package 
			GenerateFortyPackage();
		}
		List<Card> Package = Pool.remove(0);
		return Package;
	}
	boolean IsEmpty()
	{
		return Pool.isEmpty();
	}
}
