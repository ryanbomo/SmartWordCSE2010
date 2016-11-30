/*
Author: Ryan Bomalaski
Uses a hashmap to store children
 */
package SmartWord;

import java.util.HashMap;

public class TrieNode {
    // character of the trie
    char c;
    
    // HashMap containing children
    HashMap<Character, TrieNode> children = new HashMap<Character, TrieNode>();
    
    // weight of word, to be used only on end characters
    int weight = 0;
    // string of word, to be used only on end characters
    String word;
    
    public TrieNode() {
    }
    
    public TrieNode(char c){
        this.c = c;
    }
    
    public boolean hasChildren(){
        if(children.isEmpty()){
            return false;
        }else{
            return true;
        }
    }
}
