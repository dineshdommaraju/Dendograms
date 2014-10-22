import java.util.*;

class Merge{
	String city1;
	String city2;
	Merge(String city1, String city2)
	{
		this.city1=city1;
		this.city2=city2;
	}
}
public class Dendograms {
	
	static int[][] intialMatrix={{0,206,429,1504,963,2976,3095,2979,1949},
			{206,0,233,1308,802,2815,2934,2786,1771},
			{429,233,0,1075,671,2684,2799,2631,1616},
			{1504,1308,1075,0,1329,3273,3053,2687,2037},
			{963,802,671,1329,0,2013,2142,2054,996},
			{2976,2815,2684,3273,2013,0,808,1131,1307},
			{3095,2984,2799,3053,2142,808,0,379,1235},
			{2979,2786,2631,2687,2054,1131,379,0,1059},
			{1949,1771,1616,2037,996,1307,1235,1059,0}
	};
	
	static String[] cities={"BOS","NY","DC","MIA","CHI","SEA","SF","LA","DEN"};
	
	static Merge findMinimum(HashMap<String,ArrayList<String>> originalMap)
	{
		int min=Integer.MAX_VALUE;
		String city1="", city2="";
		for(String key: originalMap.keySet())
		{
		
			ArrayList<String> al=originalMap.get(key);
			for(String s: al)
			{
				
				String[] s_list=s.split(" ");
				int num=Integer.parseInt(s_list[1]);
				if(num < min)
				{
					min=num;
					city1=key;
					city2=s_list[0];				
				}
			}
		}
		//
		//
		return new Merge(city1,city2);	
	}
	
	static ArrayList<String> computeNewDistance(HashMap<String,ArrayList<String>> originaMap,String group1, String group2)
	{
		ArrayList<String> output=new ArrayList<String>();
		return output;
	}
	
	static void computeDendograms(HashMap<String,ArrayList<String>> originalMap,int level)
	{
		HashMap<String,ArrayList<String>> temp=(HashMap<String, ArrayList<String>>) originalMap.clone();
		
		while(level-- > 0)
		{
			Merge node =findMinimum(temp);
			String city1=node.city1;
			String city2=node.city2;
			System.out.println("Merge Cities " + city1+" and "+city2);
			
			//remote city1 and city2 from the hashmap and add a new group to the hashmap
			String newGroup=city1+":"+city2;
			//computeNewDistance
			//
			ArrayList<String> t1=temp.get(city1);
			ArrayList<String> t2=temp.get(city2);
			//
			//
			temp.remove(city1);
			//
			ArrayList<String> out=computeNewDistance(temp, originalMap);
			temp.put(newGroup, out);
			//removing the cities group 1 and 2
			temp.remove()
		}
		
	}
	
	static void displayOriginalHashMap(HashMap<String,ArrayList<String>> originalMap)
	{
		//debug
		//printing the hashmap
		for(String key : originalMap.keySet())
		{
			System.out.println(key);
			ArrayList<String> al=originalMap.get(key);
			for(String s : al)
				System.out.print(s+" ");
			System.out.println("\n----------");
				
		}
	}
	
	static HashMap<String,ArrayList<String>> readIntoHashMap()
	{
		HashMap<String,ArrayList<String>> originalMap=new HashMap<String, ArrayList<String>>();
		int len=cities.length;
		
		for(int i=0; i < len;i++)
		{
			for(int j=(i+1);j < len;j++)
			{
				String c1=cities[i];
				String c2=cities[j];
				
				if(originalMap.containsKey(c1))
				{
					ArrayList<String> al=originalMap.get(c1);
					String newString=c2+" "+intialMatrix[i][j];
					al.add(newString);
				}else{
					ArrayList<String> al=new ArrayList<String>();
					String newString=c2+" "+intialMatrix[i][j];
					al.add(newString);
					originalMap.put(c1,al);
				}
			}
		}	
		
		//debug
		displayOriginalHashMap(originalMap);
		return originalMap;
	}
	
	public static void main(String[] args) {
		
		HashMap<Integer, ArrayList<Integer>> hm=new HashMap<Integer,ArrayList<Integer>>();
		//computeDendograms(cities.length);
		HashMap<String,ArrayList<String>> originalMap=readIntoHashMap();
		computeDendograms(originalMap,1);
	}
}
