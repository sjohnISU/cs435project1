package cs435project1;

import java.util.BitSet;
import java.util.Random;
import java.math.BigInteger;

public class BloomFilterRan {
	private int maxSize;
	private int bits;
	private BitSet filter;
	private int numHash;
	private int filterSize;
	private int[][] functionArr;
	private int elementsAdded;

	
	public BloomFilterRan(int setSize, int bitsPerElement){
		maxSize = setSize;
		bits = bitsPerElement;
		filterSize = maxSize * bits;
		filter = new BitSet(filterSize);
		numHash = (int) Math.ceil(Math.log(2) * (filterSize/maxSize));
		functionArr = new int[numHash][];
		for (int i = 0; i < numHash; i++){
			functionArr[i] = getHash();
		}
		elementsAdded = 0;
	}
	
	//gets next prime
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
			BigInteger hash;
			BigInteger a;
			BigInteger b;
			BigInteger p;
			//loops through each hash
			for(int i = 0; i < numHash; i++){
				//sets hash to 0
				hash = BigInteger.valueOf(0);
				a = BigInteger.valueOf(this.functionArr[i][1]);
				b = BigInteger.valueOf(this.functionArr[i][2]);
				p = BigInteger.valueOf(this.functionArr[i][0]);
				a = a.multiply(BigInteger.valueOf(s.hashCode()));
				a = a.add(b);
				a = a.mod(p);
				hash = hash.add(a);
				//modulo filter size so it fits
				hash = hash.mod(BigInteger.valueOf(filterSize));
				//set hash
				filter.set(hash.intValue());
				
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
		BigInteger hash;
		BigInteger a;
		BigInteger b;
		BigInteger p;
		//loops through each hash
		for(int i = 0; i < numHash; i++){
			//sets hash to 0
			hash = BigInteger.valueOf(0);
			a = BigInteger.valueOf(this.functionArr[i][1]);
			b = BigInteger.valueOf(this.functionArr[i][2]);
			p = BigInteger.valueOf(this.functionArr[i][0]);
			//loops through each char
			a = a.multiply(BigInteger.valueOf(s.hashCode()));
			a = a.add(b);
			a = a.mod(p);
			hash = hash.add(a);
			hash = hash.mod(BigInteger.valueOf(filterSize));
			//if current check is false item is not in the set
			if (filter.get(hash.intValue()) == false){
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
