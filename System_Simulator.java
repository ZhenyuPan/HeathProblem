package HowManyPackAreNeeded;

import java.util.ArrayList;
import java.util.List;

public class System_Simulator {
	// the total number of experiment that needed for System_Symulator
	int TotalRound;
	//Let Sample.get(i) represent the total number of CardPackage that have opened before finish collection for the ith experiment
	List<Integer> Sample = new ArrayList<Integer>();
	void One_Simulation(int TotalGolden,int TotalEpic, int TotalRare, int TotalNormal)
	{
		HandCardStatus Status = new HandCardStatus(TotalGolden,TotalEpic,TotalRare,TotalNormal);
		PackagePool Pool = new PackagePool( TotalGolden, TotalEpic,  TotalRare,  TotalNormal);
		while(!Status.IsOver())
		{
			Status.OpenPackage(Pool);
		}
		System.out.println(Status.NumberOfPack);
		Sample.add(Status.NumberOfPack);
	}
	void Total_Simulation(int TotalGolden,int TotalEpic, int TotalRare, int TotalNormal)
	{
		double Sum = 0;
		for( int i = 0 ; i < TotalRound ;i++ )
		{
			One_Simulation( TotalGolden, TotalEpic,  TotalRare,  TotalNormal);
		}
		for( int i = 0 ; i < TotalRound; i++)
		{
			Sum += this.Sample.get(i);
		}
		double average = Sum/TotalRound;
		System.out.println("Average:"+average);
		double Varience=0; 
		for(int i = 0 ; i < TotalRound ; i++ )
		{
			Varience += (this.Sample.get(i)-average)*(this.Sample.get(i)-average);
		}
		Varience = Varience / (TotalRound-1);
		System.out.println("Varience:"+Varience);

	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int totalCommon = 3*2*9+22*2;
		//each class have 3 kind of common , each kind's capacity is 2 
		//and Neutral Card : 22 kind 
		int totalRare   = 3*2*9+11*2;
		//each class have 3 kind of rare , each kind's capacity is 2
		// Neutral Card: 11 kind 
		int TotalEpic   = 2*2*9 + 8*2;
		//each class have 2 kind
		// Neutral card: 8
		int TotalGolden = 2*9 +6;
		// each class have 2 legendary
		// Neutral card: 6
		System_Simulator SS = new System_Simulator();
		SS.TotalRound = 1000;
		SS.Total_Simulation(TotalGolden, TotalEpic, totalRare, totalCommon);
	}

}
