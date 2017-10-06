package cs435project1;

import java.util.BitSet;
import java.util.Random;

public class BloomFilterRan {
	int maxSize;
	int bits;
	BitSet filter;
	int numHash;
	int filterSize;
	int[][] functionArr;
	int elementsAdded;

	
	BloomFilterRan(int setSize, int bitsPerElement){
		maxSize = setSize;
		bits = bitsPerElement;
		filterSize = maxSize * bits;
		filter = new BitSet(filterSize);
		numHash = (int) Math.ceil(Math.log(2) * (filterSize/maxSize));
		functionArr = new int[numHash][];
		for (int i = 0; i < numHash; i++){
			functionArr[i] = getHash();
			//System.out.println(functionArr[i][0] + " " + functionArr[i][1] + " " + functionArr[i][2]);
		}
		elementsAdded = 0;
	}
	
	private int getNextPrime(int n){
		boolean primeFound = false;
		int nextPrime = 0;
		for (int i = n; i < 2*n; i++){
			if (i % 2 != 0){
				primeFound = true;
				for (int j = 3; j <= Math.sqrt(i); j += 2){
					if (i % j == 0){
						primeFound = false;
						break;
					}
				}
			}
			if (primeFound){
				nextPrime = i;
				break;
			}
		}
		return nextPrime;
	}
	
	private int[] getHash(){
		Random generator = new Random();
		int prime = getNextPrime(this.bits * this.maxSize);
		int a = generator.nextInt(prime);
		int b = generator.nextInt(prime);
		int[] function = {prime, a, b};
		
		return function;
	}
	
	public void add(String s){
		s = s.toLowerCase();

		if(appears(s) == false){
			int hash;
			for(int i = 0; i < numHash; i++){
				hash = 0;
				for (int j = 0; j < s.length(); j++){
					hash += (this.functionArr[i][1]*s.charAt(j) + this.functionArr[i][2]) % this.functionArr[i][0];
				}
				hash = hash % filterSize;
				if (hash < 0){
					hash += this.filterSize;
				}
				filter.set((int) hash);
				
			}
			elementsAdded++;
		}
	}
	
	public boolean appears(String s){
		s = s.toLowerCase();
		boolean check = true;
		int hash;
		for(int i = 0; i < numHash; i++){
			hash = 0;
			boolean currentCheck = false;
			for (int j = 0; j < s.length(); j++){
				hash += (this.functionArr[i][1]*s.charAt(j) + this.functionArr[i][2]) % this.functionArr[i][0];
			}
			hash = hash % filterSize;
			if (hash < 0){
				hash += this.filterSize;
			}
			currentCheck = filter.get(hash);
			if (currentCheck == false){
				check = false;
			}
		}
		return check;
	}
	
	public int filterSize(){
		return this.filterSize;
	}
	
	public int dataSize(){
		return this.elementsAdded;
	}
	
	public int numHashes(){
		return this.numHash;
	}
	public int cardinality(){
		return this.filter.cardinality();
	}
	public int length(){
		return this.filter.length();
	}
}
