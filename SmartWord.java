/*

  Authors (group members): Ryan Bomalaski
  Email addresses of group members: rbomalaski2015@my.fit.edu
  Group name: 4C

  Course: CSE 2010
  Section: 4

  Description of the overall algorithm:
1. Grab word list and build trie from the word list [Done in SmartWord]
    a. These words are then inserted into the Trie
    b. General structure for trie is used, but instead of worrying about leaves,
        the last letter of each word is given the word weight and the word string.
        This allows the ends of words to be seen logically, but saves space in not
        needing blank nodes at the end of the trie.
2. Process the old messages by chopping the file into lines, and then the lines
    into words. [Done in processOldMessages]
    a.  check to make sure each word is valid
    b. each valid word is inserted
        - On insertion, if it's not in the trie, it is added
        - If it is in the trie, then the weight is incremented by 1
3. Guess by traversing the trie and returning highest weighted words that could
    possibly start with letters typed so far
    a. Unsure of implementation, currently using a hash table where children store
        word weight in the last letter of the word
        - Could implement a priority queue for this, so that we can pop the highest 
            priority possible guesses
4. Evaluate our accuracy
        If accurate, increment weight of word by 1
        If Inaccurate but target is in trie, increment weight of correct word by 1
        If Inaccurate but target is not in trie, add to trie and set weight to 1

Sources:
    1.https://gist.github.com/kylebgorman/3099639
        -Used to help with design of Trie and TrieNode classes
    2. Our Text Book/Code from Author
        -Used for Adapatable Priority Heap imlementation
        -Default Comparator was slightly modified to prefer high value over low
    3.

 */
package SmartWord;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SmartWord {

    String[] guesses = new String[3];  // 3 guesses from SmartWord
    Trie wordBank = new Trie();
    int currentWordPos = -1;
    String currentWord;

    // initialize SmartWord with a file of English words
    public SmartWord(String wordFile) throws IOException {
        /*
        Initialize Trie using word list argument
         */
        File dumby = new File("");
        String filePath = dumby.getAbsolutePath() + "/";
        dumby.delete();
        filePath = filePath + wordFile;
        String wordsFullText = new String(Files.readAllBytes(Paths.get(filePath)));
        String lineBreak = System.getProperty("line.separator");
        String[] eachWord = wordsFullText.split(lineBreak);
        for(String w: eachWord){
            w = w.toLowerCase();
            wordBank.insert(w);
        }

    }

    // process old messages from oldMessageFile
    public void processOldMessages(String oldMessageFile) throws IOException {
        File dumby = new File("");
        String filePath = dumby.getAbsolutePath() + "/";
        dumby.delete();
        filePath = filePath + oldMessageFile;
        
        String newFullText = new String(Files.readAllBytes(Paths.get(filePath)));
        String lineBreak = System.getProperty("line.separator");
        String[] individualLines  = newFullText.split(lineBreak);
        // iterate over each line from the input
        for(int i = 0; i<individualLines.length;i++){
            String[] words = individualLines[i].split(" ");
            
            // iterate over each word on the line
            // we will check to make sure the whole word is letters
            // if it's not, isWord becomes false and we don't worry about the word
            for(int j = 0; j<words.length;j++){
                boolean isWord = true;
                
                // iterate over each character in the word
                // if a character is not a letter, ignore the whole word
                for(int k = 0; k<words[j].length();k++){
                    char thisChar = words[j].charAt(k);
                    boolean letter = Character.isLetter(thisChar);
                    if(!letter){
                        isWord = false;
                    }
                }
                
                // if the word has only letters, we insert it into the trie
                // If the word is already in the trie, it'll increase the weight
                // if not, it'll add it with a weight of 1
                if(isWord){
                    wordBank.insert(words[j]);
                }
            }
        }
    }

    // based on a letter typed in by the user, return 3 word guesses in an array
    // letter: letter typed in by the user
    // letterPosition:  position of the letter in the word, starts from 0
    // wordPosition: position of the word in a message, starts from 0
    public String[] guess(char letter, int letterPosition, int wordPosition) {
        // make sure we are still on the same word
        // if not, get stuff ready for new word
        if(wordPosition != currentWordPos){
            currentWordPos = wordPosition;
            currentWord = Character.toString(letter);
        }else{
            currentWord = currentWord + letter;
        }
        String[] guesses = wordBank.returnLikely(currentWord);
        return guesses;
    }

    // feedback on the 3 guesses from the user
    // isCorrectGuess: true if one of the guesses is correct
    // correctWord: 3 cases:
    // a.  correct word if one of the guesses is correct
    // b.  null if none of the guesses is correct, before the user has typed in 
    //            the last letter
    // c.  correct word if none of the guesses is correct, and the user has 
    //            typed in the last letter
    // That is:
    // Case       isCorrectGuess      correctWord   
    // a.         true                correct word
    // b.         false               null
    // c.         false               correct word
    public void feedback(boolean isCorrectGuess, String correctWord) {

    }

}
