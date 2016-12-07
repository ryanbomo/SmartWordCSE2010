/*
Author: Ryan Bomalaski
Uses a hashmap to store children
 */
package SmartWord;

import java.util.HashMap;

public class TrieNode {
    // character of the trie
    char letter;
    
    // array containing children
    TrieNode[] children = new TrieNode[26];
    
    // weight of word, to be used only on end characters
    int weight = 0;
    // string of word, to be used only on end characters
    String word;
    
    public TrieNode() {
    }
    
    public TrieNode(char c){
        this.letter = c;
    }
    
    public boolean hasChildren(){
        boolean hasChildren = false;
        for(TrieNode t: children){
            if(t!=null){
                hasChildren = true;
            }
        }
        
        return hasChildren;
    }
    
    public int numChildren(){
        int numChild = 0;
        for(TrieNode t: children){
            if(t!=null){
                numChild++;
            }
        }
        return numChild;
    }

}
