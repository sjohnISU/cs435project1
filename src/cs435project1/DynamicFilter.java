package cs435project1;

import java.util.BitSet;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.math.BigInteger;

public class DynamicFilter {
	List<BitSet> filterArray = new ArrayList<BitSet>();
	List<int[][]> functionArray = new ArrayList<int[][]>();
	List<Integer> numHashArray = new ArrayList<Integer>();
	List<Integer> filterSizeArray = new ArrayList<Integer>();
	List<Integer> maxSizeArray = new ArrayList<Integer>();
	int currentMaxSize = 1000;
	int bits;
	int elementsAdded;
	int currentFilter = 0;
	
	DynamicFilter(int bitsPerElement){
		bits = bitsPerElement;
		filterSizeArray.add(bits * currentMaxSize);
		filterArray.add(new BitSet(filterSizeArray.get(0)));
		numHashArray.add(2*filterSizeArray.get(0)/currentMaxSize);
		functionArray.add(new int[numHashArray.get(0)][]);
		maxSizeArray.add(currentMaxSize);
		for (int i = 0; i < numHashArray.get(0); i++){
			functionArray.get(0)[i] = getHash();
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
		int prime = getNextPrime(this.bits * this.currentMaxSize);
		int a = generator.nextInt(prime);
		int b = generator.nextInt(prime);
		int[] function = {prime, a, b};
		
		return function;
	}
	
	private void addFilter(){
		this.currentMaxSize *= 2;
		this.maxSizeArray.add(currentMaxSize);
		this.currentFilter++;
		this.filterSizeArray.add(this.currentMaxSize * this.bits);
		this.filterArray.add(new BitSet(this.filterSizeArray.get(this.currentFilter)));
		this.numHashArray.add(2*this.filterSizeArray.get(this.currentFilter)/this.currentMaxSize);
		this.functionArray.add(new int[this.numHashArray.get(this.currentFilter)][]);
		for (int i = 0; i < this.numHashArray.get(this.currentFilter); i++){
			this.functionArray.get(this.currentFilter)[i] = getHash();
		}
	}
	
	private int performFunction(String s, int hashIndex, int filterIndex){
		BigInteger hash = BigInteger.valueOf(0);
		BigInteger a = BigInteger.valueOf(this.functionArray.get(filterIndex)[hashIndex][1]);
		BigInteger b = BigInteger.valueOf(this.functionArray.get(filterIndex)[hashIndex][2]);
		BigInteger p = BigInteger.valueOf(this.functionArray.get(filterIndex)[hashIndex][0]);
		//loops through each char
		a = a.multiply(BigInteger.valueOf(s.hashCode()));
		a = a.add(b);
		a = a.mod(p);
		hash = hash.add(a);
		hash = hash.mod(BigInteger.valueOf(this.filterSizeArray.get(filterIndex)));
		return hash.intValue();
	}
	
	void add(String s){
		s = s.toLowerCase();
		if(appears(s) == false){
			int hash;
			for(int i = 0; i < this.numHashArray.get(this.currentFilter); i++){
				hash = performFunction(s, i, this.currentFilter);
				this.filterArray.get(currentFilter).set(hash);	
			}
			this.elementsAdded++;
			if (this.elementsAdded == this.currentMaxSize){
				addFilter();
			}
			//System.out.println(filterArray.get(currentFilter).toString());
		}
	}
	
	boolean appears(String s){
		s = s.toLowerCase();
		boolean check = false;
		for (int i = 0; i < this.filterArray.size(); i++){
			int hash;
			boolean filterCheck = true;
			for (int j = 0; j < this.numHashArray.get(i); j++){
				boolean hashCheck = false;
				hash = performFunction(s, j, i);
				hashCheck = this.filterArray.get(i).get(hash);
				if (hashCheck == false){
					filterCheck = false;
				}
			}
			if (filterCheck){
				check = true;
			}
		}
		return check;
	}
	
	public int filterSize(){
		int count = 0;
		for (int i = 0; i < this.currentFilter + 1; i++){
			count += this.filterSizeArray.get(i);
		}
		return count;
	}
	
	public int dataSize(){
		return this.elementsAdded;
	}
	
	public int numHashes(){
		int count = 0;
		for (int i = 0; i < this.currentFilter + 1; i++){
			count += this.numHashArray.get(i);
		}
		return count;
	}
}
