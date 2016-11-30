/*
Author: Ryan Bomalaski
 */
package SmartWord;
import java.util.HashMap;
import java.util.Map;

public class Trie {
    private TrieNode root;
    HeapAdaptablePriorityQueue wordRankings;
    
    public Trie(){
        root = new TrieNode();
    }
    
    // Used for inserting new words into the Trie
    // Also used for modifying user data.  Since we don't track whether or not
    // a word is a leaf, but rather just attach usage data to the last letter,
    // each time a word is inserted, whether it's new or not, we add one to the
    // weight of the last letter of the word.
    public void insert(String word){
        String lowerWord = word.toLowerCase();
        HashMap<Character, TrieNode> children = root.children;
        for(int i = 0; i<lowerWord.length();i++){
            char c = lowerWord.charAt(i);
            
            TrieNode t;
            if(children.containsKey(c)){
                t = children.get(c);
            }else{
                t = new TrieNode(c);
                children.put(c,t);
            }
            
            children = t.children;
            
            if(i==lowerWord.length()-1){
                t.weight++;
                t.word = lowerWord;
            }
        }
    }
    
    
    // Searches trie for a word
    public boolean search(String word){
        TrieNode t = searchNode(word);
        
        if(t !=null && t.weight>0){
            return true;
        }else{
            return false;
        }
    }
    
    // searches trie to see if there exists a word with the given prefix
    // used to check if entry will have a valid guess with the prefix
    // if it does not, it will guess the closest it can
    public boolean startsWith(String prefix){
        if(searchNode(prefix)==null){
            return false;
        }else{
            return true;
        }
    }
    
    
    // Used by other methods to find specific nodes
    public TrieNode searchNode(String str){
        Map<Character, TrieNode> children = root.children;
        TrieNode t = null;
        for(int i =0; i<str.length();i++){
            char c = str.charAt(i);
            if(children.containsKey(c)){
                t = children.get(c);
                children = t.children;
            }else{
                return null;
            }
        }
        return t;
    }
    
    public String[] returnLikely(String prefix){
        TrieNode t = searchNode(prefix);
        wordRankings = new HeapAdaptablePriorityQueue();
        recursiveHeapBuild(t);
        String[] stringArray = new String[3];
        int i = 0;
        while(!wordRankings.isEmpty() && i>3){
            stringArray[i] = wordRankings.removeMin().toString();
            i++;
        }
        return stringArray;
    }
    /*
        Go to Trie at prefix
        Make a Priority Queue of all children that have weight > 0
        Do three pops from priority queue for 3 most likely words
        return that string array
    */
    
    public void recursiveHeapBuild(TrieNode t){
        // if t has children, call recursiveHeapBuild on each child
        if(t.hasChildren()){
            for(Map.Entry<Character, TrieNode> entry : t.children.entrySet()){
                TrieNode c = entry.getValue();
                recursiveHeapBuild(c);
            }
        }
        
        // if t has weight, add word and weight to heap
        if(t.weight>0){
            wordRankings.insert(t.weight, t.word);
        }
        // doesn't return cause modifying a class variable
        // the class variable is cleared for each prefix search
        // not the cleanest way to do this, but it's easy
        
    }
    
    
}
