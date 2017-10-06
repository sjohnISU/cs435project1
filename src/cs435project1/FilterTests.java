package cs435project1;

import java.io.IOException;

public class FilterTests {
	public static void main(String[] args) throws IOException{
		BloomFilterRan myFilter = new BloomFilterRan(4,10);
		myFilter.add("map");
		myFilter.add("table");
		myFilter.add("browns");
		myFilter.add("bingo");
		
		System.out.println(myFilter.appears("map"));
		System.out.println(myFilter.appears("table"));
		System.out.println(myFilter.appears("browns"));
		System.out.println(myFilter.appears("test"));
		System.out.println(myFilter.filterSize());
		System.out.println(myFilter.dataSize());
		System.out.println(myFilter.numHashes());
		
		/*BloomFilterFNV myFilterFNV = new BloomFilterFNV(32,2);
		myFilterFNV.add("map");
		myFilterFNV.add("table");
		myFilterFNV.add("browns");
		
		System.out.println(myFilterFNV.appears("map"));
		System.out.println(myFilterFNV.appears("table"));
		System.out.println(myFilterFNV.appears("browns"));
		System.out.println(myFilterFNV.appears("test"));
		System.out.println(myFilterFNV.filterSize());
		System.out.println(myFilterFNV.dataSize());
		System.out.println(myFilterFNV.numHashes());*/
		
		/*BloomFilterMurmur myFilterMurmur = new BloomFilterMurmur(32,2);
		myFilterMurmur.add("map");
		myFilterMurmur.add("table");
		myFilterMurmur.add("browns");
		
		System.out.println(myFilterMurmur.appears("map"));
		System.out.println(myFilterMurmur.appears("table"));
		System.out.println(myFilterMurmur.appears("browns"));
		System.out.println(myFilterMurmur.appears("test"));
		System.out.println(myFilterMurmur.filterSize());
		System.out.println(myFilterMurmur.dataSize());
		System.out.println(myFilterMurmur.numHashes());*/
		
		/*DynamicFilter myDynamicFilter = new DynamicFilter(2);
		myDynamicFilter.add("map");
		myDynamicFilter.add("map");
		myDynamicFilter.add("table");
		myDynamicFilter.add("browns");
		myDynamicFilter.add("bane");
		myDynamicFilter.add("CIA");
		
		System.out.println(myDynamicFilter.appears("map"));
		System.out.println(myDynamicFilter.appears("table"));
		System.out.println(myDynamicFilter.appears("browns"));
		System.out.println(myDynamicFilter.appears("test"));
		System.out.println(myDynamicFilter.appears("Not"));
		System.out.println(myDynamicFilter.filterSize());
		System.out.println(myDynamicFilter.dataSize());
		System.out.println(myDynamicFilter.numHashes());*/
		
		/*BloomJoin bloomJ = new BloomJoin("C:\\Users\\Sam\\workspace\\cs435project1\\src\\cs435project1\\Relation1.txt",
				"C:\\Users\\Sam\\workspace\\cs435project1\\src\\cs435project1\\Relation2.txt");
		
		bloomJ.join("test");*/
	}
}
