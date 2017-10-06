package cs435project1;
import java.util.BitSet;


public class BloomFilterFNV {
	int maxSize;
	int bits;
	BitSet filter;
	int numHash;
	int filterSize;
	int elementsAdded;
	
	BloomFilterFNV(int setSize, int bitsPerElement){
		maxSize = setSize;
		bits = bitsPerElement;
		filterSize = maxSize * bits;
		filter = new BitSet(maxSize * bits);
		numHash = 2*filterSize/maxSize;
		elementsAdded = 0;
	}
	
	void add(String s){
		String capS = s.toUpperCase();
		String lowS = s.toLowerCase();
		boolean new_element = false;
		for (int i = 0; i < numHash; i++){
			long hash = 0;
			long capSHash = FNVHash.hash64(capS);
			long lowSHash = FNVHash.hash64(lowS);
			hash = (capSHash + i * lowSHash) % this.maxSize;
			if (hash < 0){
				hash += this.maxSize;
			}
			
			BitSet currentState = filter.get((int)hash*this.bits, (int)hash*this.bits + this.bits);
			BitSet full = new BitSet(this.bits);
			BitSet empty = new BitSet(this.bits);
			full.flip(0, this.bits);
			if(currentState.equals(empty)){
				new_element = true;
			}
			if (currentState.equals(full) != true){
				boolean carry = true;
				for(int k = 0; k < this.bits; k++){
					if(carry == true){
						carry = filter.get((int)hash*this.bits +k);
						if(k != this.bits - 1 || carry == false){
							filter.flip((int)hash*this.bits + k);
						}
					}
				}
			}	
			filter.set((int) hash);
		}
		if(new_element){
			elementsAdded++;
		}
	}
	
	boolean appears(String s){
		String capS = s.toUpperCase();
		String lowS = s.toLowerCase();
		boolean check = true;
		
		for (int i = 0; i < numHash; i++){
			boolean currentCheck = false;
			long hash = 0;
			long capSHash = FNVHash.hash64(capS);
			long lowSHash = FNVHash.hash64(lowS);
			hash = (capSHash + i * lowSHash) % this.maxSize;
			if (hash < 0){
				hash += this.maxSize;
			}
			
			for (int k = 0; k < this.bits; k++){
				if(filter.get((int)hash*this.bits + k)){
					currentCheck = true;
				}
			}
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
}
