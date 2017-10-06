package cs435project1;

import java.util.Random;
import java.util.Set;
import java.util.HashSet;
import java.security.SecureRandom;

//Derived from http://www.javamex.com/tutorials/collections/bloom_filter_false_positives.shtml
public class FalsePositives {

	public static void main(String[] args) {
		//sets creates random, determines the number of items to add and number of test to do
		Random r = new SecureRandom();
		int numberItems = 32000;
		int numTests = 1000000;
		
		//Bloom Filter Random
		//creates variables
		BloomFilterRan myFilterRan = new  BloomFilterRan(numberItems, 16);
		Set<String> alreadyRan = new HashSet<String>(numberItems);
		int numNotInRan = 0;
		int numFalsePositivesRan = 0;
		double falsePositiveRateRan;
		
		//add numberItems random strings to the filter and hash
		for (int i = 0; i < numberItems; i++){
			String s = randomString(r);
			myFilterRan.add(s);
			alreadyRan.add(s);
		}
		
		//for numTests random strings, check what is in the filter but not the set
		for (int n = 0; n < numTests; n++){
			String s = randomString(r);
			if(!alreadyRan.contains(s)){
				numNotInRan++;
				if(myFilterRan.appears(s)){
					numFalsePositivesRan++;
				}
			}
		}
		//get rate
		falsePositiveRateRan = ((float)numFalsePositivesRan / (float) numNotInRan);
		//print
		System.out.println(falsePositiveRateRan);
		
		//Bloom Filter FNV
		//creates variables
		BloomFilterFNV myFilterFNV = new  BloomFilterFNV(numberItems, 16);
		Set<String> alreadyFNV = new HashSet<String>(numberItems);
		int numNotInFNV = 0;
		int numFalsePositivesFNV = 0;
		double falsePositiveRateFNV;
		
		//add numberItems random strings to the filter and hash
		for (int i = 0; i < numberItems; i++){
			String s = randomString(r);
			myFilterFNV.add(s);
			alreadyFNV.add(s);
		}
		
		//for numTests random strings, check what is in the filter but not the set
		for (int n = 0; n < numTests; n++){
			String s = randomString(r);
			if(!alreadyFNV.contains(s)){
				numNotInFNV++;
				if(myFilterFNV.appears(s)){
					numFalsePositivesFNV++;
				}
			}
		}
		//get rate
		falsePositiveRateFNV = ((float)numFalsePositivesFNV / (float) numNotInFNV);
		//print
		System.out.println(falsePositiveRateFNV);
		
		//Bloom Filter Murmur
		//creates variables
		BloomFilterMurmur myFilterMurmur = new  BloomFilterMurmur(numberItems, 16);
		Set<String> alreadyMurmur = new HashSet<String>(numberItems);
		int numNotInMurmur = 0;
		int numFalsePositivesMurmur = 0;
		double falsePositiveRateMurmur;
		
		//add numberItems random strings to the filter and hash
		for (int i = 0; i < numberItems; i++){
			String s = randomString(r);
			myFilterMurmur.add(s);
			alreadyMurmur.add(s);
		}
		
		//for numTests random strings, check what is in the filter but not the set
		for (int n = 0; n < numTests; n++){
			String s = randomString(r);
			if(!alreadyMurmur.contains(s)){
				numNotInMurmur++;
				if(myFilterMurmur.appears(s)){
					numFalsePositivesMurmur++;
				}
			}
		}
		//get rate
		falsePositiveRateMurmur = ((float)numFalsePositivesMurmur / (float) numNotInMurmur);
		//print
		System.out.println(falsePositiveRateMurmur);
		
		//Dynamic Bloom Filter
		//creates variables
		DynamicFilter myFilterDynamic = new  DynamicFilter(16);
		Set<String> alreadyDynamic = new HashSet<String>(numberItems);
		int numNotInDynamic = 0;
		int numFalsePositivesDynamic = 0;
		double falsePositiveRateDynamic;
		
		//add numberItems random strings to the filter and hash
		for (int i = 0; i < numberItems; i++){
			String s = randomString(r);
			myFilterDynamic.add(s);
			alreadyDynamic.add(s);
		}
		
		//for numTests random strings, check what is in the filter but not the set
		for (int n = 0; n < numTests; n++){
			String s = randomString(r);
			if(!alreadyDynamic.contains(s)){
				numNotInDynamic++;
				if(myFilterDynamic.appears(s)){
					numFalsePositivesDynamic++;
				}
			}
		}
		//get rate
		falsePositiveRateDynamic = ((float)numFalsePositivesDynamic / (float) numNotInDynamic);
		//print
		System.out.println(falsePositiveRateDynamic);
	}

	private static final String LETTERS =
			  "abcdefghijklmnopqrstuvexyABCDEFGHIJKLMNOPQRSTUVWYXZzéèêàôû";
	
	
	//creates random strings ~len 5
	private static String randomString(Random r) {
	  int wordLen;
	  do {
	    wordLen = 5 + 2 * (int) (r.nextGaussian() + 0.5d);
	  } while (wordLen < 1 || wordLen > 12);
	  StringBuilder sb = new StringBuilder(wordLen);
	  for (int i = 0; i < wordLen; i++) {
	    char ch = LETTERS.charAt(r.nextInt(LETTERS.length()));
	    sb.append(ch);
	  }
	  return new String(sb);
	}
}
