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
		//iterates from n to 2*n
		for (int i = n; i < 2*n; i++){
			//if n is odd
			if (i % 2 != 0){
				//assume true loop through each odd number from 3 to sqrt(i)
				primeFound = true;
				for (int j = 3; j <= Math.sqrt(i); j += 2){
					//if any are divisible by j, its false
					if (i % j == 0){
						primeFound = false;
						break;
					}
				}
			}
			if (primeFound){
				//if so next prime = i
				nextPrime = i;
				break;
			}
		}
		return nextPrime;
	}
	
	//gets the hash values
	private int[] getHash(){
		Random generator = new Random();
		//gets a prime
		int prime = getNextPrime(this.bits * this.maxSize);
		//gets two random numbers from 0 to prime - 1
		int a = generator.nextInt(prime);
		int b = generator.nextInt(prime);
		//returns in an array
		int[] function = {prime, a, b};
		
		return function;
	}
	
	public void add(String s){
		//to handle case sensitivity
		s = s.toLowerCase();
		//if not already in the hash
		if(appears(s) == false){
			//creates hash value
			int hash;
			//loops through each hash
			for(int i = 0; i < numHash; i++){
				//sets hash to 0
				hash = 0;
				//loops through each char
				for (int j = 0; j < s.length(); j++){
					//performs (a*charat(j) + b) % prime and adds it to the hash
					hash += (this.functionArr[i][1]*s.charAt(j) + this.functionArr[i][2]) % this.functionArr[i][0];
				}
				//modulo filter size so it fits
				hash = hash % filterSize;
				//if hash is negative make it positive
				if (hash < 0){
					hash += this.filterSize;
				}
				//set hash
				filter.set((int) hash);
				
			}
			//increment elements added
			elementsAdded++;
		}
	}
	
	public boolean appears(String s){
		//deals with case sensitivity
		s = s.toLowerCase();
		//assume true
		boolean check = true;
		int hash;
		//loop through each hash function
		for(int i = 0; i < numHash; i++){
			//reset hash
			hash = 0;
			//assume particular check is false
			boolean currentCheck = false;
			//performs (a*charat(j) + b) % prime and adds it to the hash
			for (int j = 0; j < s.length(); j++){
				hash += (this.functionArr[i][1]*s.charAt(j) + this.functionArr[i][2]) % this.functionArr[i][0];
			}
			//modulo filter size so it fits
			hash = hash % filterSize;
			//if hash is negative make it positive
			if (hash < 0){
				hash += this.filterSize;
			}
	
			//if the hash returns a 0, the item is not in the set
			if (currentCheck = filter.get(hash)){
				check = false;
				break;
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
	
}
