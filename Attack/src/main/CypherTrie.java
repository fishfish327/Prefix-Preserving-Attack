package main;
import java.util.Random;

public class CypherTrie {
    class TrieNode {
        public boolean isWord;
        public boolean isRoot;

        public char cypherTextBit;
        public char plainTextBit;
        // two characters '0' and '1'
        public TrieNode[] children = new TrieNode[2];
    }
    private TrieNode root;

    public CypherTrie() {
        root = new TrieNode();
        root.isRoot = true;
    }

    public void insert(String plainText, String cypherText) {

        TrieNode cur = root;
        for(int i = 0; i < plainText.length(); i++){
            char plainTextBit = plainText.charAt(i);
            char cypherTextBit = cypherText.charAt(i);
            // get index of the child
            int index = cypherTextBit - '0';
            if (cur.children[index] == null) {
                cur.children[index] = new TrieNode();
                cur.children[index].plainTextBit = plainTextBit;
                cur.children[index].cypherTextBit = cypherTextBit;
            }
            cur = cur.children[index];
        }
        cur.isWord = true;
    }
    // match the longest prefix in the trie, then generate other bit randomly.
    public String guessWithMostPrefixBit(String input){
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        TrieNode cur = root;
        for(char c: input.toCharArray()){
            int index = c - '0';
            // check existence of the child
            if(index > 2 || cur.children[index] == null){
                break;
            } else {
                cur = cur.children[index];
                sb.append(cur.plainTextBit);
            }
        }
        // add random bit to get a length 32 ip address
        while (sb.length() < 32){
            sb.append(random.nextInt(2));
        }
        return sb.toString();
    }

    public static void main(String[] args){
        String plainText = IPUtil.decimalToBinary("125.56.26.110"); ;
        String cypherText = IPUtil.decimalToBinary("126.211.105.158");
        String test = IPUtil.decimalToBinary("126.217.100.207");
        CypherTrie trie = new CypherTrie();
        trie.insert(plainText, cypherText);
        System.out.println(plainText);
        System.out.println(cypherText);
        System.out.println(test);
        String guessInbinary = trie.guessWithMostPrefixBit(test);
        String guessInDecimal = IPUtil.binaryToDecimal(guessInbinary);
        System.out.println(guessInbinary);
        System.out.println(guessInDecimal);
    }
}
