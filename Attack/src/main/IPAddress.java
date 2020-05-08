package main;
/*
This class is used with BitFrequencyTrie to store its ip address and total bit can used to reconstruct other cyphertext
 */
public class IPAddress {
    public String ipInBinary;
    public String ipInDecimal;

    // sum frequency of each bit
    // eg "00001111" -> {0->8, 0->7,0->6,0->5,1->4,1->3,1->2,1->1} maxReconstructBit = 8 + 7 + 6 + 5 + 4 + 3 + 2 + 1
    public int maxReconstructBit;

    public IPAddress(String ipInBinary, String ipInDecimal ,int maxReconstructBit) {
        this.ipInBinary = ipInBinary;
        this.ipInDecimal = ipInDecimal;
        this.maxReconstructBit = maxReconstructBit;
    }
}