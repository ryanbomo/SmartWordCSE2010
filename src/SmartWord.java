/*
  Authors (group members):  Ryan Bomalaski
                            Zachariah Watkins-Wiseman
  Email addresses of group members: rbomalaski2015@my.fit.edu
                                    
  Group name: 4C
  Course: CSE 2010
  Section: 4

  Description of the overall algorithm:
1. Grab word list and build trie from the word list [Done in SmartWord]
    a. These words are then inserted into the Trie
    b. General structure for trie is used, but instead of worrying about leaves, the last letter of each word is given the word weight and the word string. This allows the ends of words to be seen logically, but saves space in not needing blank nodes at the end of the trie.

2. Process the old messages by chopping the file into lines, and then the lines into words. [Done in processOldMessages]
    a.  check to make sure each word is valid
       -If the word has ANY non-alphabet characters, the word is ignored.
       -Currently an issue of ignoring the last word of a sentence, due to punctuation.

    b. each valid word is inserted
       - On insertion, if it's not in the trie, it is added
       - If it is in the trie, then the weight is incremented by 1

3. Guess by traversing the trie and returning highest weighted words that could possibly start with letters typed so far
    a. given a letter and a word position, check to see if still on current word
    b. if still on current word, append letter
    c. if not still on current word, create new word starting with letter
    d. send word into find on trie
    e. Trie does a recursive search through all possible strings that start with the prefix
    f. Creates a priority queue with max at root, pops three times and returns those three

4. Evaluate our accuracy
    -If accurate, increment weight of word by 1
    -If Inaccurate but target is in trie, increment weight of correct word by 1
    -If Inaccurate but target is not in trie, add to trie and set weight to 1

Sources:
    1.https://gist.github.com/kylebgorman/3099639
        -Used to help with design of Trie and TrieNode classes
    2. Our Text Book/Code from Author
        -Used for Adapatable Priority Heap imlementation
        -Default Comparator was slightly modified to prefer high value over low
    3. https://github.com/ryanbomo/SmartWordCSE2010
        -Used my personal github for version control with gropu
        -I'm worried that other groups may have copied, as this isn't private
        -If I could do this all over again, I wouldn't have done this, as
         my group didn't really need the version control

Potential Improvements:
    - Fix issue in oldMessageProcessing that ignores any word with punctuatoin
      at the end
    - Fix priority heap implementation so that words with the same weight can exist
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
    
    //Storing Prior Guesses
    String[] priorGuesses = new String[3];
    
    // put weights at top for quick changes to try and find best configuration
    int dictionaryWeight = 2;
    int priorWeight = 5;
    int goodGuess = 10;
    int missedGuess = 10;
    int badGuess = -1;  // be careful changing this one, our logic requires
                        // that the weight of valid words never goes below 1

    // initialize SmartWord with a file of English words
    public SmartWord(String wordFile) throws IOException {
        /*
        Initialize Trie using word list argument
         */
        String wordsFullText = new String(Files.readAllBytes(Paths.get(wordFile)));
        String lineBreak = System.getProperty("line.separator");
        String[] eachWord = wordsFullText.split(lineBreak);
        for (String w : eachWord) {
            w = w.toLowerCase();
            wordBank.insert(w, dictionaryWeight);
        }
    }

    // process old messages from oldMessageFile
    public void processOldMessages(String oldMessageFile) throws IOException {


        String newFullText = new String(Files.readAllBytes(Paths.get(oldMessageFile)));
        String lineBreak = System.getProperty("line.separator");
        String[] individualLines = newFullText.split(lineBreak);
        // iterate over each line from the input
        for (int i = 0; i < individualLines.length; i++) {
            String[] words = individualLines[i].split(" ");

            // iterate over each word on the line
            // we will check to make sure the whole word is letters
            // if it's not, isWord becomes false and we don't worry about the word
            for (int j = 0; j < words.length; j++) {
                boolean isWord = true;

                // iterate over each character in the word
                // if a character is not a letter, ignore the whole word
                for (int k = 0; k < words[j].length(); k++) {
                    char thisChar = words[j].charAt(k);
                    boolean letter = Character.isLetter(thisChar);
                    if (!letter) {
                        isWord = false;
                    }
                }

                // if the word has only letters, we insert it into the trie
                // If the word is already in the trie, it'll increase the weight
                // if not, it'll add it with a weight of 1
                if (isWord) {
                    wordBank.insert(words[j], priorWeight);
                }
            }
        }
//        String libFilePath = filePath + "en_US_TWIT.txt";
//
//        String libFullText = new String(Files.readAllBytes(Paths.get(libFilePath)));
//        String[] individualLibLines = libFullText.split(lineBreak);
//
//        int iterator = 0;
//        while (iterator < 1) {
//            for (int i = 0; i < individualLibLines.length; i++) {
//                String[] words = individualLibLines[i].split(" ");
//
//                // iterate over each word on the line
//                // we will check to make sure the whole word is letters
//                // if it's not, isWord becomes false and we don't worry about the word
//                for (int j = 0; j < words.length; j++) {
//                    boolean isWord = true;
//
//                    // iterate over each character in the word
//                    // if a character is not a letter, ignore the whole word
//                    for (int k = 0; k < words[j].length(); k++) {
//                        char thisChar = words[j].charAt(k);
//                        boolean letter = Character.isLetter(thisChar);
//                        if (!letter) {
//                            isWord = false;
//                        }
//                    }
//
//                    // if the word has only letters, we insert it into the trie
//                    // If the word is already in the trie, it'll increase the weight
//                    // if not, it'll add it with a weight of 1
//                    if (isWord) {
//                        wordBank.insert(words[j],1);
//                    }
//                }
//            }
//            iterator++;
//        }
    }

    // based on a letter typed in by the user, return 3 word guesses in an array
    // letter: letter typed in by the user
    // letterPosition:  position of the letter in the word, starts from 0
    // wordPosition: position of the word in a message, starts from 0
    public String[] guess(char letter, int letterPosition, int wordPosition) {
        // make sure we are still on the same word
        // if not, get stuff ready for new word
        if (wordPosition != currentWordPos) {
            currentWordPos = wordPosition;
            priorGuesses = new String[3];
            currentWord = Character.toString(letter);
        } else {
            currentWord = currentWord + letter;
        }
        guesses = wordBank.returnLikely(currentWord,priorGuesses);
        priorGuesses = guesses;
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
        if (isCorrectGuess) {
            wordBank.insert(correctWord, goodGuess);
            //System.out.println(isCorrectGuess);
            //System.out.println("Correct Guess and Correct Word is: "+correctWord);
        } else if (!isCorrectGuess && correctWord != null) {
            wordBank.insert(correctWord, missedGuess);
            //System.out.println(isCorrectGuess);
            //System.out.println("Finished Typing and Correct Word is: "+correctWord);
        } else if (!isCorrectGuess && correctWord == null){
            for(String w: guesses){
                if(w!=null){
                    TrieNode t = wordBank.searchNode(w);
                    if(t!=null){
                        //find nominal amount to punish with
                        int punishAmount = badGuess;
                        while(t.weight+punishAmount<1 && punishAmount<-1){
                            punishAmount++;
                        }
                        
                        if(t.weight+punishAmount>=1){
                            wordBank.insert(w,punishAmount);
                        }
                    }
                }
            }
        }

    }
}
