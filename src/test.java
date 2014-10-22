
import java.util.*;
public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		String s="Dinesh:ku";
		String[] sa=s.split(":");
		for(String k : sa)
			System.out.println(k);
		*/
		ArrayList<Integer> al=new ArrayList<Integer>();
		al.add(1);
		al.add(2);
		HashMap<Integer, ArrayList<Integer>> hm=new HashMap<Integer, ArrayList<Integer>>();
		
		hm.put(1, al);
		ArrayList<Integer> temp=hm.get(1);
		hm.remove(1);
		System.out.println(temp.get(0));

	}

}
