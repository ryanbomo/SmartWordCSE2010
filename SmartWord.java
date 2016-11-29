/*

  Authors (group members): Ryan Bomalaski
  Email addresses of group members: rbomalaski2015@my.fit.edu
  Group name: 4C

  Course: CSE 2010
  Section: 4

  Description of the overall algorithm:
1. Grab word list and build trie from the word list [Done in SmartWord]
    a. Initial weight for each word is 0 to allow for each word to be a valid guess
        But without having to worry about whether it's been used or not
2. Process the old messages by chopping the file into lines, and then the lines
    into words. [Done in processOldMessages]
        If the word is in the trie, increment its weight by one and restructure trie
        Else put the word in the trie with weight of 1 
3. Guess by traversing the trie and returning highest weighted words that could
    possibly start with letters typed so far
4. Evaluate our accuracy
        If accurate, increment weight of word by 1
        If Inaccurate but target is in trie, increment weight of correct word by 1
        If Inaccurate but target is not in trie, add to trie and set weight to 1


 */
package SmartWord;

public class SmartWord {

    String[] guesses = new String[3];  // 3 guesses from SmartWord
    Trie wordBank = new Trie();

    // initialize SmartWord with a file of English words
    public SmartWord(String wordFile) {
        /*
        Initialize Trie using word list argument
         */
        String lineBreak = System.getProperty("line.separator");
        String[] eachWord = wordFile.split(lineBreak);
        for(String w: eachWord){
            w = w.toLowerCase();
            wordBank.insert(w);
        }

    }

    // process old messages from oldMessageFile
    public void processOldMessages(String oldMessageFile) {
        String lineBreak = System.getProperty("line.separator");
        String[] individualLines = oldMessageFile.split(lineBreak);
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
