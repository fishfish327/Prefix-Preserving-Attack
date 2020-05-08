package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ChosenPlainAttack {
    static class PriorityTrieNodeQueue {
        PriorityQueue<BitFrequencyTrie.TrieNode> pq;
        // use map to store brother node pair
        Map<BitFrequencyTrie.TrieNode, BitFrequencyTrie.TrieNode> map;

        public PriorityTrieNodeQueue() {
            pq = new PriorityQueue<>((a, b) -> b.frequency - a.frequency);
            map = new HashMap<>();
        }
        // put a node's children into pq, and store children info into map
        public void putChildNode(BitFrequencyTrie.TrieNode curr){
            BitFrequencyTrie.TrieNode nodeZero = curr.children[0];
            BitFrequencyTrie.TrieNode nodeOne = curr.children[1];
            if(nodeZero != null && nodeOne != null){
                map.put(nodeZero, nodeOne);
                map.put(nodeOne, nodeZero);
            }
            if(nodeZero != null){
                pq.add(nodeZero);
            }
            if(nodeOne != null){
                pq.add(nodeOne);
            }

        }
        public int pollAndGetFrequency(){
            int res = 0;
            BitFrequencyTrie.TrieNode curr = pq.poll();
            res += curr.frequency;
            putChildNode(curr);
            BitFrequencyTrie.TrieNode brotherNode = map.getOrDefault(curr, null);
            if(brotherNode != null){
                pq.remove(brotherNode);
                res += brotherNode.frequency;
                putChildNode(brotherNode);
                map.remove(curr);
                map.remove(brotherNode);
            }

           return res;
        }
    }
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
    public static int getReconstructBit(int n){
        PriorityTrieNodeQueue nodeQueue = new PriorityTrieNodeQueue();
        int result = 0;
        // put root into the queue
        nodeQueue.putChildNode(bitFrequencyTrie.root);
        for(int i = 0; i < n; i++){
            result += nodeQueue.pollAndGetFrequency();
        }

        return result;

    }
    public static void main(String[] args){

        if(args.length < 1){
            System.out.println("error parameter!");
            return;
        }
        String cypherFile = args[0];
        // first we build bit frequency trie;
        bitFrequencyTrie = buildFrequencyBitTrie(cypherFile);
        for(int i = 1; i < 101; i++){
            int bitCount = getReconstructBit(i);
            System.out.println("number of query: " + i + " number of bit reconstructed: " + bitCount);
        }
    }
}
