/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SmartWord;

/**
 *
 * @author ryanbomo
 */
public class TrieNode {
    TrieNode[] arr;
    boolean isLeaf;
    public TrieNode() {
        this.arr = new TrieNode[26];
    }
}
