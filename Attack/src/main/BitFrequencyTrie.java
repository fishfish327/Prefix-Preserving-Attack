package main;


import java.net.UnknownHostException;
import java.util.*;

public class BitFrequencyTrie {
    class TrieNode {
        public boolean isWord;
        public boolean isRoot;
        public int frequency;
        // temp frequency for one query
        public Integer tempFrequency;
        public char ch;
        // two characters '0' and '1'
        public TrieNode[] children = new TrieNode[2];
    }

    private TrieNode root;

    public BitFrequencyTrie() {
        root = new TrieNode();
        root.isRoot = true;
    }

    public void insert(String word) {
        // do not set frequency on root
        TrieNode cur = root;
        for (char c : word.toCharArray()) {
            int index = c - '0';
            if (cur.children[index] == null) {
                cur.children[index] = new TrieNode();
                cur.children[index].ch = c;
            }

            cur = cur.children[index];
            cur.frequency ++;
        }
        cur.isWord = true;
    }
    /*
    Max PriorityQueue with size k
     */
    public PriorityQueue<IPAddress> getKFrequentIPAddress(int k){
        PriorityQueue<IPAddress> res = new PriorityQueue<>((a, b) -> a.maxReconstructBit - b.maxReconstructBit);
        TrieNode curr = root;
        StringBuilder sb = new StringBuilder();
        LinkedList<TrieNode> frequencyList = new LinkedList<>();
        // use backtrackint to traverse the trie
        traverseTrie(curr, frequencyList, sb, res, k);

        return res;
    }

    // use backtracking to traverse the trie
    public void traverseTrie(TrieNode curr, LinkedList<TrieNode> list, StringBuilder sb, PriorityQueue<IPAddress> res, int k){
        if(curr == null){
            return;
        }

        if(curr.isWord){
            // if this node is a word, add it to the pq
            String ipInBinary = sb.toString();
            String ipInDecimal = IPUtil.binaryToDecimal(ipInBinary);
            res.add(new IPAddress(ipInBinary, ipInDecimal, list.stream().map(node -> node.tempFrequency).reduce(0, Integer::sum)));
            list.stream().forEach(node -> node.tempFrequency = 0);

            if(res.size() > k){
                res.poll();
            }
        } else {
            for(TrieNode child : curr.children){
                // add child char to the stringbuilder , add bit frequency in the list
                // after backtracking , remove the char and bit frequency in the list
                if(child != null){
                    sb.append(child.ch);
                    child.tempFrequency = child.frequency;
                    list.addLast(child);
                    traverseTrie(child, list, sb, res, k);
                    // clear stringbuilder and arraylist
                    sb.deleteCharAt(sb.length() - 1);
                    list.removeLast();
                }
            }
        }
        return;
    }

    public static void main(String[] args) throws UnknownHostException {
        String test1 = "125.33.236.191";
        String test2 = "125.131.9.127";
        String test3 = "125.133.9.181";
        System.out.println(IPUtil.decimalToBinary(test1));
        System.out.println(IPUtil.decimalToBinary(test2));
        System.out.println(IPUtil.decimalToBinary(test3));
        BitFrequencyTrie trie = new BitFrequencyTrie();
        trie.insert(IPUtil.decimalToBinary(test1));
        trie.insert(IPUtil.decimalToBinary(test2));
        trie.insert(IPUtil.decimalToBinary(test3));

        PriorityQueue<IPAddress> res = trie.getKFrequentIPAddress(1);
        for(IPAddress ip: res){
            System.out.println(ip.ipInBinary + " " + ip.ipInDecimal+" "+ ip.maxReconstructBit);
        }

    }

}

