package cs435project1;

import java.io.*;
import java.util.StringTokenizer;
import java.util.List;
import java.util.ArrayList;

public class BloomJoin {
	
	String file1;
	String file2;
	BufferedReader in1;
	BufferedReader in2;
	Pair[] relation1;
	Pair[] relation2;
	int count1;
	int count2;
	
	BloomJoin(String r1, String r2) throws IOException{
		file1 = r1;
		file2 = r2;
		try{
			in1 = new BufferedReader(new FileReader(file1));
			in2 = new BufferedReader(new FileReader(file2));
			
			String str;
			count1 = 0;
			count2 = 0;
			
			
			while((str = in1.readLine()) != null){
				count1++;
			}
			
			while((str = in2.readLine()) != null){
				count2++;
			}
			
			relation1 = new Pair[count1];
			relation2 = new Pair[count2];
			
			in1 = new BufferedReader(new FileReader(file1));
			in2 = new BufferedReader(new FileReader(file2));
			
			String[] strArr = new String[2];
			StringTokenizer token;
			int counter = 0;
			
			for (int i = 0; i < count1; i++){
				counter = 0;
				token = new StringTokenizer(in1.readLine());
				while(token.hasMoreTokens()){
					if (counter < 2){
						str = token.nextToken();
						strArr[counter] = str;
						counter++;
					}
				}
				relation1[i] = new Pair(strArr[0], strArr[1]);
			}

			for (int i = 0; i < count2; i++){
				counter = 0;
				token = new StringTokenizer(in2.readLine());
				while(token.hasMoreTokens()){
					if (counter < 2){
						str = token.nextToken();
						strArr[counter] = str;
						counter++;
					}
				}
				relation2[i] = new Pair(strArr[0], strArr[1]);
			}
			
			System.out.println(relation1[0].getLeft());
			System.out.println(relation2[0].getRight());
			
		}catch (IOException e){
			e.printStackTrace();
		}
		
	}
	
	void join(String r3){
		BloomFilterRan myFilter = new BloomFilterRan(count1, 16);
		List<Pair> toJoin = new ArrayList<Pair>();
		for(int i = 0; i < count1; i++){
			myFilter.add(relation1[i].getLeft());
		}
		
		/*for(int i = 0; i < count2; i++){
			if(myFilter.appears(relation2[i].getLeft())){
				toJoin.add(relation2[i]);
			}
		}*/
		
		System.out.println(myFilter.filterSize());
		System.out.println(myFilter.numHashes());
		System.out.println(myFilter.dataSize());
		System.out.println(myFilter.cardinality());
		System.out.println(myFilter.length());
		//System.out.println(toJoin.get(0).getLeft() + " " + toJoin.get(0).getRight());
		//System.out.println(toJoin.size());
	}
	
}
