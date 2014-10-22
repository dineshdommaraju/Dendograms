/*
 * @author: Dinesh Dommaraju
 * Program Description:
 */
import java.util.*;

//class to describe the two merged nodes at each level
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
	static HashMap<String,Integer> Map=new HashMap<String, Integer>();
	
	//Finds the minimum distance between two groups which needs to be clustered
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
	
	//Method to update the distance when groups were merged.
	static ArrayList<String> computeNewDistance(HashMap<String,ArrayList<String>> temp,String group1,String mode)
	{
		ArrayList<String> output=new ArrayList<String>();
		String[] group1_arr=group1.split(":");
		
		for(String keys: temp.keySet())
		{
			String[] key_arr=keys.split(":");
			
			ArrayList<Integer> tempList=new ArrayList<Integer>();
			for(String key: key_arr)
			{
				if(!group1.contains(key))
				{	
					for(String city: group1_arr)
					{
						int dis=Map.get(city+":"+key);
						tempList.add(dis);
					}
				}
			}
			//
			//
			if(tempList.size() > 0)
			{
				Collections.sort(tempList);
				int size=tempList.size()/2;
				int update_distance;
				if(mode.equals("small")) update_distance=tempList.get(0);
				else if(mode.equals("complete")) update_distance=tempList.get(tempList.size()-1);
				else{
					update_distance=tempList.get(size);
				}
					
				output.add(keys+" "+update_distance);
			}	
		}
		return output;
	}
	
	static void computeDendograms(HashMap<String,ArrayList<String>> originalMap,int level,String mode)
	{
		HashMap<String,ArrayList<String>> temp=(HashMap<String, ArrayList<String>>) originalMap.clone();
		int l=0;
		while(level-- > 0)
		{
			++l;
			Merge node =findMinimum(temp);
			String city1=node.city1;
			String city2=node.city2;
			//System.out.println("Merge Cities " + city1+" and "+city2);
			System.out.println("_____________________________________");
			System.out.println("Level "+l);
			System.out.println("_____________________________________");
			//remote city1 and city2 from the hashmap and add a new group to the hashmap
			String newGroup=city1+":"+city2;
			//computeNewDistance
			//
			//removing the cities group 1 and 2
			temp.remove(city1);
			temp.remove(city2);
			//
			//
			ArrayList<String> out=computeNewDistance(temp,newGroup,mode);
			//
			//removing city1 and city2 from all the lists
			//
			for(String key : temp.keySet())
			{
				ArrayList<String> al=temp.get(key);
				
				for(int i=0; i < al.size();i++)
				{
					String t=al.get(i);
					if(t.contains(city1) || t.contains(city2))
					{
						al.remove(i);
						--i;
					}
				}
			}
			temp.put(newGroup, out);
			//
			displayOriginalHashMap(temp);
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
				Map.put(c1+":"+c2,intialMatrix[i][j]);
				Map.put(c2+":"+c1,intialMatrix[i][j]);
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
		
		originalMap.put(cities[len-1], new ArrayList<String>());
		//debug
		System.out.println("Level0");
		displayOriginalHashMap(originalMap);
		return originalMap;
	}
	
	public static void main(String[] args) {
		
		HashMap<Integer, ArrayList<Integer>> hm=new HashMap<Integer,ArrayList<Integer>>();
		//computeDendograms(cities.length);
		HashMap<String,ArrayList<String>> originalMap=readIntoHashMap();
		System.out.println("Single link clustering");
		computeDendograms(originalMap,cities.length-1,"small");
		System.out.println("Complete clustering");
		System.out.println("************************");
		computeDendograms(originalMap,cities.length-1,"Complete");
		System.out.println("Median link clustering");
		System.out.println("************************");
		computeDendograms(originalMap,cities.length-1,"Median");
	}
}