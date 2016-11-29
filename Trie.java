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
public class Trie {
    private TrieNode root;
    public Trie(){
        root = new TrieNode();
    }
    
    public void insert(String word){
        TrieNode r = root;
        for (int i =0; i<word.length(); i++){
            char c = word.charAt(i);
            int index = c-'a';
            if(r.arr[index]==null){
                TrieNode temp = new TrieNode();
                r.arr[index] = temp;
                r = temp;
            }else{
                r=r.arr[index];
            }
        }
        r.isLeaf = true;
    }
    
    public boolean search(String word){
        TrieNode p = searchNode(word);
        if(p==null){
            return false;
        }else{
            if(p.isLeaf){
                return true;
            }
        }
        return false;
    }
    
    public boolean startsWith(String prefix){
        TrieNode p = searchNode(prefix);
        if(p==null){
            return false;
        }else{
            return true;
        }
    }
    
    public TrieNode searchNode(String s){
        TrieNode p = root;
        for(int i = 0; i<s.length(); i++){
            char c = s.charAt(i);
            int index = c-'a';
            if(p.arr[index]!=null){
                p=p.arr[index];
            }else{
                return null;
            }
        }
        
        if(p==root){
            return null;
        }
        return p;
    }
}
