package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ChosenCipherAttack {
    public static BitFrequencyTrie bitFrequencyTrie;

    public static BitFrequencyTrie buildFrequencyBitTrie(String fileName){
        BitFrequencyTrie bitFrequencyTrie = new BitFrequencyTrie();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            while (true){
                String line = bufferedReader.readLine();
                if(line == null){
                    break;
                }
                bitFrequencyTrie.insert(IPUtil.decimalToBinary(line));

            }
            bufferedReader.close();
        } catch (IOException ex){
            ex.printStackTrace();
        }
        System.out.println("##### FrequencyBitTrie generated!");
        return bitFrequencyTrie;
    }

    public static CypherTrie buildCypherTrie(String queryFile, int k){
        PriorityQueue<IPAddress> pq = bitFrequencyTrie.getKFrequentIPAddress(k);
        CypherTrie cypherTrie = new CypherTrie();
        Set<String> ipSet = new HashSet<>();
        // put top k frequency ip in the set
        for(IPAddress ipAddress: pq){
            ipSet.add(ipAddress.ipInDecimal);
        }
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(queryFile));
            while (true){
                // iterate all lines to get top k cyphertext and its plaintext
                String line = bufferedReader.readLine();
                if(line == null){
                    break;
                }
                String[] str = line.split(",");
                String cypherText = str[0];
                String plainText = str[1];
                if(ipSet.contains(cypherText)){
                    ipSet.remove(cypherText);
                    // first param plaintext, second param cyphertext
                    // convert all text to binary format first
                    cypherTrie.insert(IPUtil.decimalToBinary(plainText), IPUtil.decimalToBinary(cypherText));
                }


            }
            bufferedReader.close();
        } catch (IOException ex){
            ex.printStackTrace();
        }
        System.out.println("##### CypherTrie generated!");
        return cypherTrie;

    }
    /*
    In this function, we iterate lines in cypherPlainFile
    We use cypherText and a cypherTrie to get a guessText
    Then we calculate the longest prefix matching between guessText and plainText
     */
    public static int getReconstructBit(String cypherPlainFile, int n){

        // generate cypher trie
        CypherTrie cypherTrie = buildCypherTrie(cypherPlainFile, n);
        Integer matchBitCount = 0;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(cypherPlainFile));
            while (true){
                // iterate all lines to get top k cyphertext and its plaintext
                String line = bufferedReader.readLine();
                if(line == null){
                    break;
                }

                String[] str = line.split(",");
                String cypherText = IPUtil.decimalToBinary(str[0]);
                String plainText = IPUtil.decimalToBinary(str[1]);
                String guessText = cypherTrie.guessWithMostPrefixBit(cypherText);
                int match = 0;
                // count match bit between plaintext and guesstest
                for(int i = 0; i < plainText.length(); i++){
                    if(plainText.charAt(i) == guessText.charAt(i)){
                        match ++;
                    } else {
                        break;
                    }
                }
                matchBitCount += match;
            }
            bufferedReader.close();
        } catch (IOException ex){
            ex.printStackTrace();
        }
        return matchBitCount;

    }


    public static void main(String[] args) {

        String cypherFile = "/home/ted/Prefix-Preserving-Attack/yacryptopan/result.txt";
        String cypherPlainFile = "/home/ted/Prefix-Preserving-Attack/yacryptopan/cypherplain.txt";
        // first we build bit frequency trie;
        bitFrequencyTrie = buildFrequencyBitTrie(cypherFile);
        for(int i = 1; i < 101; i++){
            int bitCount = getReconstructBit(cypherPlainFile, i);
            System.out.println("number of query: " + i + " number of bit reconstructed: " + bitCount);
        }



    }

}
