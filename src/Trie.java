/*
Author: Ryan Bomalaski
 */
package SmartWord;

import java.util.HashMap;
import java.util.Map;

public class Trie {

    private TrieNode root;
    HeapPriorityQueue wordRankings;

    public Trie() {
        root = new TrieNode();
    }

    // Used for inserting new words into the Trie
    // Also used for modifying user data.  Since we don't track whether or not
    // a word is a leaf, but rather just attach usage data to the last letter,
    // each time a word is inserted, whether it's new or not, we add one to the
    // weight of the last letter of the word.
    public void insert(String word, int weightMod) {
        // convert to lower and set pointer to root
        String lowerWord = word.toLowerCase();
        HashMap<Character, TrieNode> children = root.children;
        // for each letter in the word, travel down from the root
        for (int i = 0; i < lowerWord.length(); i++) {
            char c = lowerWord.charAt(i);
            TrieNode t;
            // if the children have the node, go to it
            if (children.containsKey(c)) {
                t = children.get(c);
            } else {
                // if the children don't have the node, create it
                t = new TrieNode(c);
                children.put(c, t);
            }
            // point to children
            children = t.children;

            // Once you get to the end of the word
            // add the weight to the node of the last letter
            // this marks unique words and allows us to quickly get the weight
            // this allows our insertion method to also be used to iterate word
            // weight.
            if (i == lowerWord.length() - 1) {
                t.weight = t.weight + weightMod;
                t.word = lowerWord;
            }
        }
    }

    // Used by other methods to find specific nodes
    public TrieNode searchNode(String str) {
        String lowerWord = str.toLowerCase();
        // create a map of the children starting at the root
        Map<Character, TrieNode> children = root.children;
        TrieNode t = null;
        // read through the children until you find the bottom of the word
        // or you find a mismatch
        for (int i = 0; i < lowerWord.length(); i++) {
            char c = lowerWord.charAt(i);
            if (children.containsKey(c)) {
                t = children.get(c);
                children = t.children;
            } else {
                // couldn't find word
                return null;
            }
        }
        // if full word was found, return t
        return t;
    }

    public String[] returnLikely(String prefix, String[] priorAttempts) {
        TrieNode t = searchNode(prefix);

        // typos and words that are not yet in the dicitonary will cause an issue
        // this just adds the prefix and returns that as the only guess if we've
        // reached a point where the prefix is not in the Trie
        if (t == null) {
            String[] stringArray = new String[3];
            stringArray[1] = prefix;
            return stringArray;
        }

        // This creates a new priority heap
        wordRankings = new HeapPriorityQueue();

        // recursively search through the trie to build the heap
        recursiveHeapBuild(t);

        // reinitialize the guesses to be returned
        String[] stringArray = new String[3];
        int i = 0;

        // pop the three most likely guesses from the heap
        while (!wordRankings.isEmpty() && i < 3) {
            stringArray[i] = wordRankings.removeMin().getValue().toString();
            i++;
        }

        // check to see if we've already guessed this word
        for (i = 0; i < stringArray.length; i++) {
            for (String v : priorAttempts) {
                if (v != null && stringArray[i] != null) {
                    if (stringArray[i].equals(v) && !wordRankings.isEmpty()) {
                        stringArray[i] = wordRankings.removeMin().getValue().toString();
                    }
                }
            }
        }
        // Section for Debugging
        // System.out.println();
        // System.out.println("Prefix: "+prefix);
        // System.out.println("Your guesses are:");
        // for(String s: stringArray){
        //     System.out.println(s);
        // }
        // System.out.println();
        return stringArray;
    }

    // This is a recursive algorithm to look through each node in a subtree
    // Given the root node, this looks at each child, grabs any that
    // have weight > 0 (which for us means are the end of a word)
    // and puts the stored word into the priority queue with the weight as the key
    public void recursiveHeapBuild(TrieNode t) {
        // if t has children, call recursiveHeapBuild on each child
        if (!t.children.isEmpty()) {
            for (Map.Entry<Character, TrieNode> entry : t.children.entrySet()) {
                TrieNode c = entry.getValue();
                recursiveHeapBuild(c);
            }
        }
        // if t has weight, add word and weight to heap
        // currently has an issue where if two possible guesses have
        // same weight, only one is put into the heap
        // it's a key vs value issue
        if (t.weight > 0) {
            wordRankings.insert(t.weight, t.word);
        }
        // doesn't return cause modifying a class variable
        // the class variable is cleared for each prefix search
        // not the cleanest way to do this, but it's easy   
    }
}
