package cs435project1;
import java.util.BitSet;
import java.math.BigInteger;

public class BloomFilterFNV {
	private int maxSize;
	private int bits;
	private BitSet filter;
	private int numHash;
	private int filterSize;
	private int elementsAdded;
	
	public BloomFilterFNV(int setSize, int bitsPerElement){
		maxSize = setSize;
		bits = bitsPerElement;
		filterSize = maxSize * bits;
		filter = new BitSet(maxSize * bits);
		numHash = (int) Math.ceil(Math.log(2) * (filterSize/maxSize));
		elementsAdded = 0;
	}
	//adds a string
	public void add(String s){
		//stores upper and lowercase value of S
		String capS = s.toUpperCase();
		String lowS = s.toLowerCase();
		if(appears(s) == false){
			for (int i = 0; i < numHash; i++){
				//creates hash by taking hash(UPPERCASE) + hash(LOWERCASE) * i % size of the bloom filter
				//as explained here: http://willwhim.wpengine.com/2011/09/03/producing-n-hash-functions-by-hashing-only-once/
				BigInteger hash;
				BigInteger capSHash = BigInteger.valueOf(FNVHash.hash64(capS));
				BigInteger lowSHash = BigInteger.valueOf(FNVHash.hash64(lowS));
				hash = capSHash.add(lowSHash.multiply(BigInteger.valueOf(i)));
				hash = hash.mod(BigInteger.valueOf(this.filterSize));	
				//sets hash value
				filter.set(hash.intValue());
			}
			//increases number added by 1
			elementsAdded++;
		}
	}
	
	public boolean appears(String s){
		//stores upper and lowercase value of S
		String capS = s.toUpperCase();
		String lowS = s.toLowerCase();
		//keeps track of all hash checks, if any are false, this returns false
		boolean check = true;
		for (int i = 0; i < numHash; i++){
			//creates hash by taking hash(UPPERCASE) + hash(LOWERCASE) * i % size of the bloom filter
			//as explained here: http://willwhim.wpengine.com/2011/09/03/producing-n-hash-functions-by-hashing-only-once/
			BigInteger hash;
			BigInteger capSHash = BigInteger.valueOf(FNVHash.hash64(capS));
			BigInteger lowSHash = BigInteger.valueOf(FNVHash.hash64(lowS));
			hash = capSHash.add(lowSHash.multiply(BigInteger.valueOf(i)));
			hash = hash.mod(BigInteger.valueOf(this.filterSize));
			//if returns false, the value is not in the filter so we can break the for loop
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
